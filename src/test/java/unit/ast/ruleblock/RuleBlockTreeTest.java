package unit.ast.ruleblock;

import ast.AbstractAstNode;
import ast.ruleblock.BodyNode;
import ast.ruleblock.CondNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IfNode;
import ast.ruleblock.IsNode;
import ast.ruleblock.NotNode;
import ast.ruleblock.RuleBlockNode;
import ast.ruleblock.Weight;
import ast.ruleblock.WithNode;
import ast.ruleblock.operators.MaxNode;
import ast.ruleblock.operators.MinNode;
import ast.ruleblock.operators.ProdNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.AstTestSupport.lines;
import static utils.AstTestSupport.render;

class RuleBlockTreeTest {

    @Test
    void FullRuleBlock_IsRenderedAsExpected() {
        assertEquals(lines(
                "rule_temperature",
                "  MAX",
                "    IF 1",
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
                "      IF 2",
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
        IfNode firstRule = new IfNode("1", new CondNode(firstCondition), new BodyNode(firstAct));

        MaxNode secondCondition = new MaxNode(Arrays.asList(
                is("humidity", "high"),
                is("color", "light")
        ));
        ProdNode secondAct = new ProdNode(Collections.singletonList(
                is("dT1", "positive")
        ));
        IfNode secondRule = new IfNode("2", new CondNode(secondCondition), new BodyNode(secondAct));
        WithNode secondRuleWithWeight = new WithNode(Weight.numeric("0.5"), secondRule);

        MaxNode accumulation = new MaxNode(Arrays.asList(firstRule, secondRuleWithWeight));
        return new RuleBlockNode("rule_temperature", accumulation);
    }

    private static IsNode is(final String variable, final String term) {
        return new IsNode(new IdentifierNode(variable), new IdentifierNode(term));
    }
}
