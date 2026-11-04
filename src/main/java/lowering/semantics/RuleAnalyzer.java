package lowering.semantics;

import ast.AbstractAstNode;
import ast.ruleblock.RuleBlockNode;
import ast.ruleblock.operators.Operator;
import ast.ruleblock.operators.OperatorNode;
import codegen.evaluation.MinMaxMethod;
import lowering.builders.ControllerModelBuilder;
import lowering.contexts.RuleLoweringContext;
import lowering.facades.RuleLower;
import lowering.contexts.LoweringContext;
import utils.diagnostics.UnsupportedFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuleAnalyzer implements SemanticAnalyzer {
    private final LoweringContext ctx;

    public RuleAnalyzer(LoweringContext ctx) {
        this.ctx = ctx;
    }

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
