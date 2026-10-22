package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import parser.initializations.leafs.SubrangeInitialization;
import parser.initializations.leafs.VariableInitialization;
import utils.Block;
import utils.ParserTestSupport;
import utils.SourceCode;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Collections;

/**
 * Integration tests for SUBRANGE declarations: named types, inline variables and struct members.
 * The default value of a subrange is its lower bound.
 */
public class SubrangeIT extends ParserTestSupport {

    private static final String DAY_TYPE = "day : INT (0..31)";

    private static LexemeInfoBuilder subrange(Use use, Source source, String low, String high) {
        return new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(Subtype.INT)
                .use(use)
                .source(source)
                .inferiorLimits(Collections.singletonList(low))
                .superiorLimits(Collections.singletonList(high));
    }

    private static LexemeInfoBuilder variableOfDay(Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("DAY")
                .use(Use.VARIABLE)
                .source(block.source());
    }

    @Nested
    class AsTypeDeclaration {
        @Test
        void Subrange_type_publishes_its_bounds_and_defaults_to_the_lower_bound() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().type(DAY_TYPE));

            assertSymbol(st, "DAY", subrange(Use.TYPE, Source.NONE, "0", "31")
                    .initialValue(new SubrangeInitialization("0", "31"))
                    .build());
        }
    }

    @Nested
    class AsVariable {
        @ParameterizedTest
        @EnumSource(Block.class)
        void Inline_subrange_publishes_its_bounds_and_defaults_to_the_lower_bound(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().var(block, "percentage : INT (0..100)"));

            assertSymbol(st, SourceCode.key("percentage"), subrange(Use.VARIABLE, block.source(), "0", "100")
                    .initialValue(new SubrangeInitialization("0", "100"))
                    .build());
        }

        @ParameterizedTest
        @EnumSource(Block.class)
        void Inline_subrange_with_initializer_stores_the_literal(Block block) throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock().var(block, "percentage : INT (0..100) := 50"));

            assertSymbol(st, SourceCode.key("percentage"), subrange(Use.VARIABLE, block.source(), "0", "100")
                    .initialValue(new VariableInitialization("50"))
                    .build());
        }

        @Test
        void Variable_of_a_named_subrange_inherits_the_type_default() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(DAY_TYPE)
                    .var(Block.VAR, "work_day : day"));

            assertSymbol(st, SourceCode.key("work_day"), variableOfDay(Block.VAR)
                    .initialValue(new SubrangeInitialization("0", "31"))
                    .build());
        }

        @Test
        void Variable_of_a_named_subrange_keeps_the_type_reference_and_uses_its_initializer() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type(DAY_TYPE)
                    .var(Block.VAR, "work_day : day := 21"));

            assertSymbol(st, SourceCode.key("work_day"), variableOfDay(Block.VAR)
                    .initialValue(new VariableInitialization("21"))
                    .build());
        }
    }

    @Nested
    class AsStructField {
        @Test
        void Subrange_field_publishes_its_bounds_and_defaults_to_the_lower_bound() throws Exception {
            SymbolTable st = parse(SourceCode.functionBlock()
                    .type("gauge_type : STRUCT level : INT (0..10); END_STRUCT"));

            assertSymbol(st, "GAUGE_TYPE#LEVEL", subrange(Use.FIELD, Source.NONE, "0", "10")
                    .initialValue(new SubrangeInitialization("0", "10"))
                    .build());
        }
    }

    @Nested
    class Errors {
        @Test
        @NotYetImplemented
        void Lower_bound_greater_than_upper_bound_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock().var(Block.VAR, "v : INT (10..5)"));
        }

        /** Already rejected by the grammar: a bound must be a numeric constant. */
        @Test
        void Non_constant_bound_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock().var(Block.VAR, "v : INT (limit..10)"));
        }

        @Test
        @NotYetImplemented
        void Initializer_out_of_bounds_is_rejected() throws Exception {
            assertRejected(SourceCode.functionBlock().var(Block.VAR, "v : INT (0..10) := 15"));
        }
    }
}
