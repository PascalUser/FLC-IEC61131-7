package integration.lexer.parser.ast;

import ast.AbstractSyntaxTree;
import ast.ruleblock.CondNode;
import ast.ruleblock.IdentifierNode;
import ast.ruleblock.IfNode;
import ast.ruleblock.RuleBlockNode;
import ast.ruleblock.WithNode;
import ast.ruleblock.operators.Operator;
import ast.ruleblock.operators.OperatorNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.ParserTestSupport;
import utils.ParsingResults;
import utils.SourceCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.AstTestSupport.lines;
import static utils.AstTestSupport.render;

/**
 * Integration tests for RULEBLOCK AST construction, written against the architecture defined in
 * {@code Parser.y}.
 *
 * <p>
 * Rule numbers are not represented in the AST, and operators are built as generic
 * {@link OperatorNode}s rather than as the concrete subclasses used by the AST unit tests.
 * </p>
 */
public class RuleBlockIT extends ParserTestSupport {

    private static final String DEFAULT_OPERATORS = "    ;";
    private static final String DEFAULT_ACCUMULATION = "    ACCU: MAX;";

    private static String ruleBlock(
            String name, String operators, String activation, String accumulation, String... rules
    ) {
        StringBuilder block = new StringBuilder();
        block.append("RULEBLOCK ").append(name).append('\n');
        block.append(operators).append('\n');
        if (!activation.isEmpty()) {
            block.append(activation).append('\n');
        }
        block.append(accumulation).append('\n');
        for (String rule : rules) {
            block.append(rule).append('\n');
        }
        block.append("END_RULEBLOCK");
        return block.toString();
    }

    private static String defaultRuleBlock(String... rules) {
        return ruleBlock("inference", DEFAULT_OPERATORS, "", DEFAULT_ACCUMULATION, rules);
    }

    private static String rule(int number, String condition, String conclusions) {
        return "    RULE " + number + ": IF " + condition + " THEN " + conclusions + ";";
    }

    private static String rule(String weight) {
        return "    RULE " + 1 + ": IF " + "humidity IS middle" + " THEN " + "dT1 IS zero" + " WITH " + weight + ";";
    }

    private static RuleBlockNode onlyRuleBlock(AbstractSyntaxTree ast) {
        assertEquals(1, ast.children().size());
        return (RuleBlockNode) ast.children().get(0);
    }

    private static IfNode onlyRule(AbstractSyntaxTree ast) {
        RuleBlockNode ruleBlock = onlyRuleBlock(ast);
        assertEquals(1, ruleBlock.accumulation().children().size());
        return (IfNode) ruleBlock.accumulation().children().get(0);
    }

    private static CondNode condition(AbstractSyntaxTree ast) {
        return onlyRule(ast).condition();
    }

    private static OperatorNode activation(AbstractSyntaxTree ast) {
        return onlyRule(ast).body().act();
    }

    private AbstractSyntaxTree parseRules(String... rules) throws Exception {
        ParsingResults results = parse(
                SourceCode.functionBlock()
                        .body(defaultRuleBlock(rules))
        );
        return results.abstractSyntaxTree();
    }

    @Nested
    class AsTree {

        @Test
        void Empty_function_block_has_an_empty_root() throws Exception {
            ParsingResults results = parse(SourceCode.functionBlock());
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            assertEquals("ROOT", ast.label());
            assertTrue(ast.children().isEmpty());
        }

        @Test
        void Ruleblock_is_scoped_with_the_function_block_name() throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock())
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();
            RuleBlockNode ruleBlock = onlyRuleBlock(ast);

            assertEquals("MAIN#INFERENCE", ruleBlock.label());
            assertTrue(ruleBlock.accumulation().children().isEmpty());
        }

        @Test
        void Multiple_ruleblocks_keep_source_order() throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(ruleBlock("first", DEFAULT_OPERATORS, "", DEFAULT_ACCUMULATION))
                            .body(ruleBlock("second", DEFAULT_OPERATORS, "", DEFAULT_ACCUMULATION))
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            assertEquals(2, ast.children().size());
            assertEquals("MAIN#FIRST", ast.children().get(0).label());
            assertEquals("MAIN#SECOND", ast.children().get(1).label());
        }

        @Test
        void Empty_ruleblock_has_an_empty_accumulation_operator() throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock())
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();
            OperatorNode accumulation = onlyRuleBlock(ast).accumulation();

            assertEquals(Operator.MAX, accumulation.operator());
            assertTrue(accumulation.children().isEmpty());
        }

        @Test
        void Simple_rule_builds_accumulation_if_condition_and_body() throws Exception {
            String expectedTree = lines(
                    "ROOT",
                    "  MAIN#INFERENCE",
                    "    MAX",
                    "      IF",
                    "        COND",
                    "          IS",
                    "            HUMIDITY",
                    "            MIDDLE",
                    "        BODY",
                    "          MIN",
                    "            IS",
                    "              DT1",
                    "              ZERO"
            );

            AbstractSyntaxTree ast = parseRules(rule(7, "humidity IS middle", "dT1 IS zero"));

            assertEquals(expectedTree, render(ast));
        }

        @Test
        void Multiple_rules_preserve_source_order() throws Exception {
            String expectedTree = lines(
                    "ROOT",
                    "  MAIN#INFERENCE",
                    "    MAX",
                    "      IF",
                    "        COND",
                    "          IS",
                    "            HUMIDITY",
                    "            LOW",
                    "        BODY",
                    "          MIN",
                    "            IS",
                    "              DT1",
                    "              ZERO",
                    "      IF",
                    "        COND",
                    "          IS",
                    "            HUMIDITY",
                    "            HIGH",
                    "        BODY",
                    "          MIN",
                    "            IS",
                    "              DT1",
                    "              POSITIVE"
            );

            AbstractSyntaxTree ast = parseRules(
                    rule(1, "humidity IS low", "dT1 IS zero"),
                    rule(2, "humidity IS high", "dT1 IS positive")
            );

            assertEquals(expectedTree, render(ast));
        }

        @Test
        void Multiple_ruleblocks_with_rules_preserve_source_order() throws Exception {
            String expectedTree = lines(
                    "ROOT",
                    "  MAIN#FIRST",
                    "    MAX",
                    "      IF",
                    "        COND",
                    "          IS",
                    "            HUMIDITY",
                    "            LOW",
                    "        BODY",
                    "          MIN",
                    "            IS",
                    "              DT1",
                    "              ZERO",
                    "  MAIN#SECOND",
                    "    MAX",
                    "      IF",
                    "        COND",
                    "          IS",
                    "            HUMIDITY",
                    "            HIGH",
                    "        BODY",
                    "          MIN",
                    "            IS",
                    "              DT1",
                    "              POSITIVE"
            );

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(ruleBlock("first", DEFAULT_OPERATORS, "", DEFAULT_ACCUMULATION,
                                    rule(1, "humidity IS low", "dT1 IS zero")))
                            .body(ruleBlock("second", DEFAULT_OPERATORS, "", DEFAULT_ACCUMULATION,
                                    rule(1, "humidity IS high", "dT1 IS positive")))
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            assertEquals(expectedTree, render(ast));
        }
    }

    @Nested
    class Conditions {

        @Test
        void Simple_is_condition_builds_an_is_node() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  IS",
                    "    HUMIDITY",
                    "    MIDDLE"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Is_not_condition_builds_not_over_is() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  NOT",
                    "    IS",
                    "      HUMIDITY",
                    "      MIDDLE"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS NOT middle", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Not_condition_negates_a_subcondition() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  NOT",
                    "    IS",
                    "      HUMIDITY",
                    "      MIDDLE"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "NOT (humidity IS middle)", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Bare_identifier_condition_builds_an_identifier_node() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  HUMIDITY"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity", "dT1 IS zero"));

            assertInstanceOf(IdentifierNode.class, condition(ast).condition());
            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void And_condition_uses_the_configured_operator() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  MIN",
                    "    IS",
                    "      HUMIDITY",
                    "      MIDDLE",
                    "    IS",
                    "      COLOR",
                    "      BROWN"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle AND color IS brown", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Or_condition_uses_the_configured_operator() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  MAX",
                    "    IS",
                    "      HUMIDITY",
                    "      MIDDLE",
                    "    IS",
                    "      COLOR",
                    "      BROWN"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle OR color IS brown", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Parentheses_do_not_add_a_node() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  IS",
                    "    HUMIDITY",
                    "    MIDDLE"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "(humidity IS middle)", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Or_has_lower_precedence_than_and() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  MAX",
                    "    IS",
                    "      HUMIDITY",
                    "      MIDDLE",
                    "    MIN",
                    "      IS",
                    "        COLOR",
                    "        BROWN",
                    "      IS",
                    "        TEMPERATURE",
                    "        HIGH"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle OR color IS brown AND temperature IS high", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }

        @Test
        void Chained_and_is_right_nested() throws Exception {
            String expectedAst = lines(
                    "COND",
                    "  MIN",
                    "    IS",
                    "      HUMIDITY",
                    "      LOW",
                    "    MIN",
                    "      IS",
                    "        COLOR",
                    "        BROWN",
                    "      IS",
                    "        TEMPERATURE",
                    "        HIGH"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS low AND color IS brown AND temperature IS high", "dT1 IS zero"));

            assertEquals(expectedAst, render(condition(ast)));
        }
    }

    @Nested
    class Conclusions {

        @Test
        void Explicit_conclusion_builds_an_is_node() throws Exception {
            String expectedAst = lines(
                    "MIN",
                    "  IS",
                    "    DT1",
                    "    ZERO"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle", "dT1 IS zero"));

            assertEquals(expectedAst, render(activation(ast)));
        }

        @Test
        void Implicit_conclusion_builds_an_identifier_node() throws Exception {
            String expectedAst = lines(
                    "MIN",
                    "  DT1"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle", "dT1"));

            assertInstanceOf(IdentifierNode.class, activation(ast).children().get(0));
            assertEquals(expectedAst, render(activation(ast)));
        }

        @Test
        void Comma_separated_conclusions_preserve_order() throws Exception {
            String expectedAst = lines(
                    "MIN",
                    "  IS",
                    "    DT1",
                    "    ZERO",
                    "  IS",
                    "    DT2",
                    "    POSITIVE",
                    "  DT3"
            );

            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle", "dT1 IS zero, dT2 IS positive, dT3"));

            assertEquals(expectedAst, render(activation(ast)));
        }

        @Test
        void Numeric_with_builds_a_numeric_weight() throws Exception {
            AbstractSyntaxTree ast = parseRules(rule("0.5"));
            WithNode with = (WithNode) onlyRuleBlock(ast).accumulation().children().get(0);

            assertEquals(".5", with.weight());
            assertEquals("WITH .5", with.label());
            assertInstanceOf(IfNode.class, with.ifNode());
        }

        @Test
        void Identifier_with_preserves_the_identifier_kind() throws Exception {
            AbstractSyntaxTree ast = parseRules(rule("weight"));
            WithNode with = (WithNode) onlyRuleBlock(ast).accumulation().children().get(0);

            assertEquals("MAIN#WEIGHT", with.weight());
        }
    }

    @Nested
    class Operators {

        @Test
        void Default_operators_are_max_min_and_min() throws Exception {
            AbstractSyntaxTree ast = parseRules(rule(1, "humidity IS middle OR color IS brown AND temperature IS high", "dT1 IS zero"));

            OperatorNode or = (OperatorNode) condition(ast).condition();
            OperatorNode and = (OperatorNode) or.children().get(1);

            assertEquals(Operator.MAX, or.operator());
            assertEquals(Operator.MIN, and.operator());
            assertEquals(Operator.MIN, activation(ast).operator());
            assertEquals(Operator.MAX, onlyRuleBlock(ast).accumulation().operator());
        }

        @Test
        void Explicit_or_and_act_and_accu_are_applied() throws Exception {
            String operators = String.join("\n",
                    "    OR: ASUM",
                    "    AND: PROD;");

            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(ruleBlock("inference", operators, "    ACT: PROD;", "    ACCU: BSUM;",
                                    rule(1, "humidity IS middle OR color IS brown AND temperature IS high", "dT1 IS zero")))
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            OperatorNode or = (OperatorNode) condition(ast).condition();
            OperatorNode and = (OperatorNode) or.children().get(1);

            assertEquals(Operator.ASUM, or.operator());
            assertEquals(Operator.PROD, and.operator());
            assertEquals(Operator.PROD, activation(ast).operator());
            assertEquals(Operator.BSUM, onlyRuleBlock(ast).accumulation().operator());
        }

        @ParameterizedTest(name = "ACCU: {0}")
        @CsvSource({"MAX", "BSUM", "NSUM"})
        void Accumulation_operator_is_preserved(String operator) throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(ruleBlock("inference", DEFAULT_OPERATORS, "", "    ACCU: " + operator + ";"))
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            assertEquals(Operator.valueOf(operator), onlyRuleBlock(ast).accumulation().operator());
        }

        @Test
        void Operator_node_is_generic() throws Exception {
            ParsingResults results = parse(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock())
            );
            AbstractSyntaxTree ast = results.abstractSyntaxTree();

            assertEquals(OperatorNode.class, onlyRuleBlock(ast).accumulation().getClass());
        }

        @Test
        void Missing_accu_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(String.join("\n",
                                    "RULEBLOCK inference",
                                    "    ;",
                                    "END_RULEBLOCK"))
            );
        }

        @Test
        void Invalid_ruleblock_operator_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(String.join("\n",
                                    "RULEBLOCK inference",
                                    "    OR: MIN;",
                                    "    ACCU: MAX;",
                                    "END_RULEBLOCK"))
            );
        }
    }

    @Nested
    class Errors {

        @Test
        void Missing_rule_number_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock("    RULE: IF humidity IS middle THEN dT1 IS zero;"))
            );
        }

        @Test
        void Missing_then_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock("    RULE 1: IF humidity IS middle dT1 IS zero;"))
            );
        }

        @Test
        void Missing_semicolon_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock("    RULE 1: IF humidity IS middle THEN dT1 IS zero"))
            );
        }

        @Test
        void Missing_end_ruleblock_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(String.join("\n",
                                    "RULEBLOCK inference",
                                    "    ;",
                                    "    ACCU: MAX;"))
            );
        }

        @Test
        void Malformed_condition_is_rejected() throws Exception {
            assertRejected(
                    SourceCode.functionBlock()
                            .body(defaultRuleBlock("    RULE 1: IF humidity IS THEN dT1 IS zero;"))
            );
        }
    }
}