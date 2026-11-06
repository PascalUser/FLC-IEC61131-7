package unit.ast.ruleblock;

import compiler.ast.AbstractAstNode;
import compiler.ast.ruleblock.BodyNode;
import compiler.ast.ruleblock.CondNode;
import compiler.ast.ruleblock.IdentifierNode;
import compiler.ast.ruleblock.IfNode;
import compiler.ast.ruleblock.IsNode;
import compiler.ast.ruleblock.NotNode;
import compiler.ast.ruleblock.RuleBlockNode;
import compiler.ast.ruleblock.WithNode;
import compiler.ast.ruleblock.operators.MaxNode;
import compiler.ast.ruleblock.operators.MinNode;
import compiler.ast.ruleblock.operators.ProdNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.supports.AstTestSupport.lines;
import static utils.supports.AstTestSupport.render;

class RuleBlockTreeTest {

    @Test
    void FullRuleBlock_IsRenderedAsExpected() {
        assertEquals(lines(
                "rule_temperature",
                "  MAX",
                "    IF",
                "      COND",
                "        MIN",
                "          IS",
                "            humidity",
                "            middle",
                "          NOT",
                "            IS",
                "              color",
                "              brown",
                "      BODY",
                "        PROD",
                "          IS",
                "            dT1",
                "            zero",
                "          IS",
                "            dT2",
                "            zero",
                "    WITH 0.5",
                "      IF",
                "        COND",
                "          MAX",
                "            IS",
                "              humidity",
                "              high",
                "            IS",
                "              color",
                "              light",
                "        BODY",
                "          PROD",
                "            IS",
                "              dT1",
                "              positive"
        ), render(fullRuleBlock()));
    }

    @Test
    void IsNot_IsRepresentedAsNotOverIs() {
        AbstractAstNode tree = new NotNode(is("color", "brown"));

        assertEquals(lines(
                "NOT",
                "  IS",
                "    color",
                "    brown"
        ), render(tree));
    }

    @Test
    void ParenthesizedCondition_DoesNotAddANode() {
        AbstractAstNode tree = new NotNode(new MaxNode(Arrays.asList(
                is("humidity", "high"),
                is("color", "light")
        )));

        assertEquals(lines(
                "NOT",
                "  MAX",
                "    IS",
                "      humidity",
                "      high",
                "    IS",
                "      color",
                "      light"
        ), render(tree));
    }

    private static RuleBlockNode fullRuleBlock() {
        MinNode firstCondition = new MinNode(Arrays.asList(
                is("humidity", "middle"),
                new NotNode(is("color", "brown"))
        ));
        ProdNode firstAct = new ProdNode(Arrays.asList(
                is("dT1", "zero"),
                is("dT2", "zero")
        ));
        IfNode firstRule = new IfNode(new CondNode(firstCondition), new BodyNode(firstAct));

        MaxNode secondCondition = new MaxNode(Arrays.asList(
                is("humidity", "high"),
                is("color", "light")
        ));
        ProdNode secondAct = new ProdNode(Collections.singletonList(
                is("dT1", "positive")
        ));
        IfNode secondRule = new IfNode(new CondNode(secondCondition), new BodyNode(secondAct));
        WithNode secondRuleWithWeight = new WithNode("0.5", secondRule);

        MaxNode accumulation = new MaxNode(Arrays.asList(firstRule, secondRuleWithWeight));
        return new RuleBlockNode("rule_temperature", accumulation);
    }

    private static IsNode is(final String variable, final String term) {
        return new IsNode(new IdentifierNode(variable), new IdentifierNode(term));
    }
}
