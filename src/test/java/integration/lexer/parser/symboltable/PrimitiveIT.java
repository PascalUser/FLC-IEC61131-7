package integration.lexer.parser.symboltable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import parser.initializations.leafs.VariableInitialization;
import utils.Block;
import utils.LexemeInfo;
import utils.ParserTestSupport;
import utils.SourceCode;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for variables of primitive types (BOOL, integers, reals, STRING, WSTRING) declared in
 * every variable block, with and without initializer.
 * <p>
 * Default values are asserted as literal text ("0.0", "FALSE", ...), not rebuilt with the production
 * factory, so a wrong default cannot hide behind the same bug in the expectation.
 * </p>
 */
public class PrimitiveIT extends ParserTestSupport {

    /** One primitive type with its textual default and an initializer literal. */
    static final class Primitive {
        final String typeName;
        final Subtype subtype;
        final String defaultValue;
        final String literal;
        final String storedLiteral;

        Primitive(String typeName, Subtype subtype, String defaultValue, String literal, String storedLiteral) {
            this.typeName = typeName;
            this.subtype = subtype;
            this.defaultValue = defaultValue;
            this.literal = literal;
            this.storedLiteral = storedLiteral;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    private static final List<Primitive> PRIMITIVES = Arrays.asList(
            new Primitive("BOOL", Subtype.BOOL, "FALSE", "TRUE", "TRUE"),
            // The lexer normalizes 0.3e10 to .3e10
            new Primitive("REAL", Subtype.REAL, "0.0", "0.3e10", ".3e10"),
            new Primitive("LREAL", Subtype.LREAL, "0.0", "0.3e10", ".3e10"),
            new Primitive("SINT", Subtype.SINT, "0", "42", "42"),
            new Primitive("INT", Subtype.INT, "0", "42", "42"),
            new Primitive("DINT", Subtype.DINT, "0", "42", "42"),
            new Primitive("LINT", Subtype.LINT, "0", "42", "42"),
            new Primitive("USINT", Subtype.USINT, "0", "42", "42"),
            new Primitive("UINT", Subtype.UINT, "0", "42", "42"),
            new Primitive("UDINT", Subtype.UDINT, "0", "42", "42"),
            new Primitive("ULINT", Subtype.ULINT, "0", "42", "42"),
            new Primitive("STRING", Subtype.STRING, "''", "'text'", "'text'"),
            new Primitive("WSTRING", Subtype.WSTRING, "\"\"", "\"text\"", "\"text\"")
    );

    static Stream<Arguments> blocksAndPrimitives() {
        List<Arguments> arguments = new ArrayList<>();
        for (Block block : Block.values()) {
            for (Primitive primitive : PRIMITIVES) {
                arguments.add(Arguments.of(block, primitive));
            }
        }
        return arguments.stream();
    }

    private static LexemeInfoBuilder variable(Subtype subtype, Block block) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .use(Use.VARIABLE)
                .source(block.source());
    }

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("blocksAndPrimitives")
    void Uninitialized_primitive_variable_gets_its_literal_default(Block block, Primitive primitive)
            throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().var(block, "var1 : " + primitive.typeName));

        assertSymbolWithValue(st, SourceCode.key("var1"), variable(primitive.subtype, block), primitive.defaultValue);
    }

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("blocksAndPrimitives")
    void Initialized_primitive_variable_keeps_the_normalized_literal(Block block, Primitive primitive)
            throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock()
                .var(block, "var1 : " + primitive.typeName + " := " + primitive.literal));

        assertSymbol(st, SourceCode.key("var1"), variable(primitive.subtype, block)
                .initialValue(new VariableInitialization(primitive.storedLiteral))
                .build());
    }

    @Test
    void Every_identifier_in_a_list_is_published_with_the_same_metadata() throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().var(Block.VAR, "first, second : INT"));

        for (String identifier : new String[]{"first", "second"}) {
            assertSymbolWithValue(st, SourceCode.key(identifier), variable(Subtype.INT, Block.VAR), "0");
        }
    }

    @Test
    void Variables_take_the_source_of_the_block_they_are_declared_in() throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock()
                .var(Block.VAR_INPUT, "in1 : INT")
                .var(Block.VAR_OUTPUT, "out1 : REAL")
                .var(Block.VAR, "local1 : BOOL")
                .var(Block.VAR, "local2 : BOOL"));

        assertSymbolWithValue(st, SourceCode.key("in1"), variable(Subtype.INT, Block.VAR_INPUT), "0");
        assertSymbolWithValue(st, SourceCode.key("out1"), variable(Subtype.REAL, Block.VAR_OUTPUT), "0.0");
        assertSymbolWithValue(st, SourceCode.key("local1"), variable(Subtype.BOOL, Block.VAR), "FALSE");
        assertSymbolWithValue(st, SourceCode.key("local2"), variable(Subtype.BOOL, Block.VAR), "FALSE");
    }

    /**
     * Known gap: {@code Factory.createPrimitiveInitialization} only supports BOOL, REAL, LREAL, STRING, WSTRING
     * and integers, so declaring a TIME/DATE/bit-string variable throws IllegalArgumentException.
     */
    @ParameterizedTest
    @EnumSource(value = Subtype.class, names = {
            "TIME", "DATE", "TIME_OF_DAY", "DATE_AND_TIME", "BYTE", "WORD", "DWORD", "LWORD"
    })
    @NotYetImplemented
    void Temporal_and_bit_string_variables_are_published_with_their_subtype(Subtype subtype) throws Exception {
        SymbolTable st = parse(SourceCode.functionBlock().var(Block.VAR, "var1 : " + subtype.name()));

        LexemeInfo info = st.get(SourceCode.key("var1"));
        assertNotNull(info);
        assertEquals(subtype, info.subtype);
    }

    // ---------------------------------------------------------------- errors

    @Test
    @NotYetImplemented
    void Real_variable_initialized_with_a_string_is_rejected() throws Exception {
        assertRejected(SourceCode.functionBlock().var(Block.VAR, "var1 : REAL := 'text'"));
    }

    /** Already enforced by the grammar: {@code initialized_boolean} only accepts a boolean constant. */
    @Test
    void Bool_variable_initialized_with_an_integer_is_rejected() throws Exception {
        assertRejected(SourceCode.functionBlock().var(Block.VAR, "var1 : BOOL := 42"));
    }
}
