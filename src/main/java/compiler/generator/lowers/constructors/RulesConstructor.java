package compiler.generator.lowers.constructors;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.RuleBlockNode;
import compiler.ast.ruleblock.operators.Operator;
import compiler.ast.ruleblock.operators.OperatorNode;
import compiler.generator.evaluation.MinMaxMethod;
import compiler.generator.lowers.builders.ControllerModelBuilder;
import compiler.generator.lowers.contexts.RuleLoweringContext;
import compiler.generator.lowers.RuleLower;
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
public final class RulesConstructor implements ModelConstructor {
    private final LoweringContext ctx;

    /**
     * Creates the stage that lowers the declared rule blocks.
     *
     * @param ctx lowering context shared by the stages
     */
    public RulesConstructor(LoweringContext ctx) {
        this.ctx = ctx;
    }

    /** {@inheritDoc} */
    @Override
    public boolean construct(ControllerModelBuilder controller) {
        List<AbstractAstNode> ruleblocks = ctx.ast.children();
        int ruleblocksCount = ruleblocks.size();
        if (ruleblocksCount != 1) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_BLOCK_AMOUNT(ruleblocksCount)));
            return false;
        }
        RuleBlockNode declaredRuleblock = (RuleBlockNode) ruleblocks.get(0);
        OperatorNode accumulationMethod = declaredRuleblock.accumulation();
        String ruleblockName = ctx.keys.shortName(declaredRuleblock.label());
        if (accumulationMethod.operator() != Operator.MAX) {
            String accuMethod = accumulationMethod.label();
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_ACCU(ruleblockName, accuMethod)));
            return false;
        }
        if (accumulationMethod.children().isEmpty()) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_RULES_MISSING(ruleblockName)));
            return false;
        }
        int number = 0;
        List<int[]> loweredRules = new ArrayList<>();
        for (AbstractAstNode rule : accumulationMethod.children()) {
            RuleLoweringContext ruleLoweringCtx = new RuleLoweringContext(
                    ctx,
                    controller,
                    ruleblockName,
                    String.valueOf(++number),
                    rule
            );
            RuleLower ruleLower = new RuleLower(ruleLoweringCtx);
            Optional<int[]> ruleAsRow = ruleLower.adapt();
            if (!ruleAsRow.isPresent()) {
                return false;
            }
            loweredRules.add(ruleAsRow.get());
        }
        controller.evaluationMethod(new MinMaxMethod(loweredRules));
        return true;
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
