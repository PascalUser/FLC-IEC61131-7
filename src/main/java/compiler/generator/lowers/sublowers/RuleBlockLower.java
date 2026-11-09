package compiler.generator.lowers.sublowers;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.RuleBlockNode;
import compiler.ast.ruleblock.operators.Operator;
import compiler.ast.ruleblock.operators.OperatorNode;
import compiler.generator.evaluation.EvaluationMethod;
import compiler.generator.evaluation.MinMaxMethod;
import compiler.generator.lowers.Lower;
import compiler.generator.lowers.contexts.RuleLoweringContext;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.repository.diagnostics.UnsupportedFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Builds the rule matrix of a controller from the single declared rule block.
 * <p>
 * Only one rule block with {@code ACCU: MAX} is supported; every rule becomes one row of the matrix.
 * </p>
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RuleBlockLower implements Lower<EvaluationMethod> {
    private final LoweringContext ctx;

    public RuleBlockLower(LoweringContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Optional<EvaluationMethod> adapt() {
        List<AbstractAstNode> ruleblocks = ctx.ast.children();
        if (ruleblocks.size() != 1) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_BLOCK_AMOUNT(ruleblocks.size())));
            return Optional.empty();
        }
        RuleBlockNode block = (RuleBlockNode) ruleblocks.get(0);
        OperatorNode accumulation = block.accumulation();
        String blockName = ctx.keys.shortName(block.label());
        if (accumulation.operator() != Operator.MAX) {
            String accuMethod = accumulation.label();
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_ACCU(blockName, accuMethod)));
            return Optional.empty();
        }
        if (accumulation.children().isEmpty()) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_RULES_MISSING(blockName)));
            return Optional.empty();
        }
        boolean valid = true;
        int number = 0;
        List<int[]> rows = new ArrayList<>();
        for (AbstractAstNode rule : accumulation.children()) {
            String strRuleNumber = String.valueOf(++number);
            RuleLoweringContext ruleCtx = new RuleLoweringContext(ctx, blockName, strRuleNumber, rule);
            Optional<int[]> row = new RuleLower(ruleCtx).adapt();
            if (row.isPresent()) rows.add(row.get());
            else valid = false;
        }
        return valid ? Optional.of(new MinMaxMethod(rows)) : Optional.empty();
    }

    private static String ERR_UNSUPPORTED_BLOCK_AMOUNT(int blocksAmount) {
        return "exactly one RULEBLOCK is supported (Basic Level), found " + blocksAmount;
    }

    private static String ERR_UNSUPPORTED_ACCU(String ruleblockName, String accuMethod) {
        return "Only MAX method is supported but " + accuMethod + " was found in " + ruleblockName;
    }

    private static String ERR_RULES_MISSING(String ruleblockName) {
        return ruleblockName + " has no rules";
    }
}
