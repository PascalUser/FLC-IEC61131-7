package unit.parser.semantics;

import compiler.ast.ruleblock.*;
import compiler.ast.ruleblock.operators.MaxNode;
import compiler.ast.ruleblock.operators.MinNode;
import compiler.parser.initializations.leafs.VariableInitialization;
import compiler.parser.initializations.nodes.StructInitialization;
import compiler.parser.semantics.*;
import compiler.parser.utils.IdentifierResolver;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Source;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SemanticAnalyzerTest {
    private final DiagnosticsHandler diagnostics = new DiagnosticsHandler();
    private final SymbolTable table = new SymbolTable();

    private int reported() { return diagnostics.getDiagnostics().size(); }
    private static List<String> list(String... v) { return Arrays.asList(v); }
    private static LexemeInfo info(Type type, Use use, Source source, List<String> parameters) {
        return new LexemeInfoBuilder().type(type).use(use).source(source).parameters(parameters).build();
    }

    @Nested
    class DistinctNamesTest {
        @Test
        void Different_names_pass() {
            DistinctNames checker = new DistinctNames(diagnostics);
            List<String> names = list("A", "B");

            boolean isValid = checker.check(names);
            int reportedCount = reported();

            assertTrue(isValid);
            assertEquals(0, reportedCount);
        }

        @Test
        void Repeated_name_is_reported_once_per_repetition() {
            DistinctNames checker = new DistinctNames(diagnostics);
            List<String> names = list("A", "B", "A");

            boolean isValid = checker.check(names);
            int reportedCount = reported();
            String output = diagnostics.getOutput();

            assertFalse(isValid);
            assertEquals(1, reportedCount);
            assertTrue(output.contains("duplicate name A"));
        }
    }

    @Nested
    class FreshKeysTest {
        @Test
        void Absent_key_passes() {
            FreshKeys checker = new FreshKeys(table, diagnostics);
            List<String> keys = list("X");

            boolean isValid = checker.check(keys);

            assertTrue(isValid);
        }

        @Test
        void Raw_identifier_with_unknown_use_passes() {
            table.put("X", info(Type.UNKNOWN, Use.UNKNOWN, Source.UNKNOWN, null));
            FreshKeys checker = new FreshKeys(table, diagnostics);
            List<String> keys = list("X");

            boolean isValid = checker.check(keys);

            assertTrue(isValid);
        }

        @Test
        void Declared_key_is_reported() {
            table.put("X", info(Type.SIMPLE, Use.VARIABLE, Source.IN, null));
            FreshKeys checker = new FreshKeys(table, diagnostics);
            List<String> keys = list("X");

            boolean isValid = checker.check(keys);
            String output = diagnostics.getOutput();

            assertFalse(isValid);
            assertTrue(output.contains("duplicate declaration of X"));
        }
    }

    @Nested
    class DeclaredTypeTest {
        @Test
        void Type_passes() {
            table.put("T", info(Type.SIMPLE, Use.TYPE, Source.NONE, null));
            DeclaredType checker = new DeclaredType(table, diagnostics);

            boolean isValid = checker.check("T");

            assertTrue(isValid);
        }

        @Test
        void Variable_is_not_a_type() {
            table.put("V", info(Type.SIMPLE, Use.VARIABLE, Source.IN, null));
            DeclaredType checker = new DeclaredType(table, diagnostics);

            boolean isValid = checker.check("V");

            assertFalse(isValid);
        }

        @Test
        void Absent_name_is_reported() {
            DeclaredType checker = new DeclaredType(table, diagnostics);

            boolean isValid = checker.check("Z");
            String output = diagnostics.getOutput();

            assertFalse(isValid);
            assertTrue(output.contains("undeclared type Z"));
        }
    }

    @Nested
    class OrderedBoundsTest {
        @Test
        void Ordered_and_equal_bounds_pass() {
            OrderedBounds checker = new OrderedBounds(diagnostics);
            List<String> bounds = list("0", "10", "5", "5");

            boolean isValid = checker.check(bounds);

            assertTrue(isValid);
        }

        @Test
        void Reversed_bound_is_reported() {
            OrderedBounds checker = new OrderedBounds(diagnostics);
            List<String> bounds = list("10", "0");

            boolean isValid = checker.check(bounds);
            int reportedCount = reported();

            assertFalse(isValid);
            assertEquals(1, reportedCount);
        }

        @Test
        void Second_dimension_is_checked() {
            OrderedBounds checker = new OrderedBounds(diagnostics);
            List<String> bounds = list("0", "5", "3", "1");

            boolean isValid = checker.check(bounds);

            assertFalse(isValid);
        }

        @Test
        void Non_literal_bounds_are_skipped() {
            OrderedBounds checker = new OrderedBounds(diagnostics);
            List<String> bounds = list("", "3");

            boolean isValid = checker.check(bounds);

            assertTrue(isValid);
        }
    }

    @Nested
    class EnumeratorOfTypeTest {
        @BeforeEach
        void declare() {
            table.put("COLOR", info(Type.ENUMERATE, Use.TYPE, Source.NONE, list("RED", "GREEN")));
        }

        @Test
        void Enumerator_passes() {
            EnumeratorOfType checker = new EnumeratorOfType(table, diagnostics, "COLOR");

            boolean isValid = checker.check("RED");

            assertTrue(isValid);
        }

        @Test
        void Other_identifier_is_reported() {
            EnumeratorOfType checker = new EnumeratorOfType(table, diagnostics, "COLOR");

            boolean isValid = checker.check("BLUE");

            assertFalse(isValid);
        }

        @Test
        void Non_enumerated_type_is_reported() {
            table.put("S", info(Type.STRUCT, Use.TYPE, Source.NONE, list("RED")));
            EnumeratorOfType checker = new EnumeratorOfType(table, diagnostics, "S");

            boolean isValid = checker.check("RED");

            assertFalse(isValid);
        }
    }

    @Nested
    class ConstantAcceptingTypeTest {
        @Test
        void Subrange_and_simple_types_accept_constants() {
            table.put("R", info(Type.SUBRANGE, Use.TYPE, Source.NONE, null));
            table.put("I", info(Type.SIMPLE, Use.TYPE, Source.NONE, null));
            ConstantAcceptingType checker = new ConstantAcceptingType(table, diagnostics);

            boolean isSubrangeValid = checker.check("R");
            boolean isSimpleValid = checker.check("I");

            assertTrue(isSubrangeValid);
            assertTrue(isSimpleValid);
        }

        @Test
        void Enumeration_structure_and_array_do_not() {
            for (Type type : new Type[]{Type.ENUMERATE, Type.STRUCT, Type.ARRAY}) {
                table.put("T", info(type, Use.TYPE, Source.NONE, null));
                ConstantAcceptingType checker = new ConstantAcceptingType(table, diagnostics);

                boolean isValid = checker.check("T");

                assertFalse(isValid, type.name());
            }
        }
    }

    @Nested
    class ExistingFieldTest {
        private StructInitialization rgb() {
            StructInitialization s = new StructInitialization();
            s.put("R", new VariableInitialization("1"));
            StructInitialization outer = new StructInitialization();
            outer.put("X", new VariableInitialization("0"));
            outer.put("RGB", s);
            return outer;
        }

        @Test
        void Field_and_nested_field_pass() {
            ExistingField checker = new ExistingField(diagnostics, rgb());

            boolean isXValid = checker.check("X");
            boolean isRgbRValid = checker.check("RGB#R");

            assertTrue(isXValid);
            assertTrue(isRgbRValid);
        }

        @Test
        void Unknown_field_is_reported() {
            ExistingField checker = new ExistingField(diagnostics, rgb());

            boolean isValid = checker.check("Y");
            String output = diagnostics.getOutput();

            assertFalse(isValid);
            assertTrue(output.contains("unknown field Y"));
        }

        @Test
        void Leaf_has_no_fields() {
            ExistingField checker = new ExistingField(diagnostics, new VariableInitialization("1"));

            boolean isValid = checker.check("X");

            assertFalse(isValid);
        }
    }

    @Nested
    class RuleChecks {
        private final IdentifierResolver idResolver = new IdentifierResolver("MAIN");

        @BeforeEach
        void declare() {
            table.put("MAIN#A", info(Type.SIMPLE, Use.VARIABLE, Source.IN, null));
            table.put("MAIN#Y", info(Type.SIMPLE, Use.VARIABLE, Source.OUT, null));
            table.put("MAIN#A@fuzzifier", info(Type.FUNCTION, Use.COMPONENT, Source.FUZZIFY, list("LO", "HI")));
            table.put("MAIN#Y@defuzzifier", info(Type.FUNCTION, Use.COMPONENT, Source.DEFUZZIFY, list("OFF", "ON")));
        }

        private IsNode is(String v, String t) {
            return new IsNode(
                    new IdentifierNode(idResolver.key(v)),
                    new IdentifierNode(idResolver.key(t))
            );
        }
        private RuleBlockNode rule(IsNode condition, IsNode conclusion) {
            IfNode ifNode = new IfNode(new CondNode(condition), new BodyNode(new MinNode(Collections.singletonList(conclusion))));
            return new RuleBlockNode("MAIN#R", new MaxNode(Collections.singletonList(ifNode)));
        }

        @Test
        void Valid_rule_passes_both_checks() {
            RuleBlockNode block = rule(is("A", "LO"), is("Y", "ON"));
            RuleVariableRoles rolesChecker = new RuleVariableRoles(table, diagnostics, idResolver);
            RuleTermsBelongToVariables termsChecker = new RuleTermsBelongToVariables(table, diagnostics, idResolver);

            boolean areRolesValid = rolesChecker.check(block);
            boolean areTermsValid = termsChecker.check(block);
            int reportedCount = reported();

            assertTrue(areRolesValid);
            assertTrue(areTermsValid);
            assertEquals(0, reportedCount);
        }

        @Test
        void Output_in_a_condition_is_reported() {
            RuleBlockNode block = rule(is("Y", "ON"), is("Y", "ON"));
            RuleVariableRoles rolesChecker = new RuleVariableRoles(table, diagnostics, idResolver);

            boolean areRolesValid = rolesChecker.check(block);
            String output = diagnostics.getOutput();

            assertFalse(areRolesValid);
            assertTrue(output.contains("rule 1 of R uses Y"));
        }

        @Test
        void Undeclared_variable_is_reported() {
            RuleBlockNode block = rule(is("A", "LO"), is("C", "ON"));
            RuleVariableRoles rolesChecker = new RuleVariableRoles(table, diagnostics, idResolver);

            boolean areRolesValid = rolesChecker.check(block);
            String output = diagnostics.getOutput();

            assertFalse(areRolesValid);
            assertTrue(output.contains("uses C"));
        }

        @Test
        void Term_of_another_variable_is_reported() {
            RuleBlockNode block = rule(is("A", "MID"), is("Y", "ON"));
            RuleTermsBelongToVariables termsChecker = new RuleTermsBelongToVariables(table, diagnostics, idResolver);

            boolean areTermsValid = termsChecker.check(block);
            String output = diagnostics.getOutput();

            assertFalse(areTermsValid);
            assertTrue(output.contains("in rule 1 of R, the MID is not from A"));
        }

        @Test
        void Terms_check_skips_a_variable_with_the_wrong_role_so_the_error_is_not_reported_twice() {
            // Y is an output used in a condition: wrong role. ON is a valid term of Y.
            RuleBlockNode block = rule(is("Y", "ON"), is("Y", "ON"));
            RuleVariableRoles rolesChecker = new RuleVariableRoles(table, diagnostics, idResolver);
            RuleTermsBelongToVariables termsChecker = new RuleTermsBelongToVariables(table, diagnostics, idResolver);

            rolesChecker.check(block);
            int reportedByRoles = reported();
            termsChecker.check(block);
            int reportedAfterTerms = reported();

            assertEquals(1, reportedByRoles, "the role error is reported once by RuleVariableRoles");
            assertEquals(reportedByRoles, reportedAfterTerms, "RuleTermsBelongToVariables adds nothing for it");
        }

        @Test
        void Missing_fuzzify_block_is_left_to_the_lowering() {
            table.put("MAIN#B", info(Type.SIMPLE, Use.VARIABLE, Source.IN, null));
            RuleBlockNode block = rule(is("B", "ANYTHING"), is("Y", "ON"));
            RuleTermsBelongToVariables termsChecker = new RuleTermsBelongToVariables(table, diagnostics, idResolver);

            boolean areTermsValid = termsChecker.check(block);

            assertTrue(areTermsValid);
        }

        @Test
        void Weighted_rule_is_checked_too() {
            IfNode inner = new IfNode(new CondNode(is("A", "MID")), new BodyNode(new MinNode(Collections.singletonList(is("Y", "ON")))));
            RuleBlockNode block = new RuleBlockNode("MAIN#R", new MaxNode(Collections.singletonList(new WithNode(".5", inner))));
            RuleTermsBelongToVariables termsChecker = new RuleTermsBelongToVariables(table, diagnostics, idResolver);

            boolean areTermsValid = termsChecker.check(block);

            assertFalse(areTermsValid);
        }
    }
}