package integration.lexer.parser.symboltable;

import parser.initializations.Factory;
import parser.initializations.primitives.*;
import utils.ParserTestSupport;

import parser.initializations.VariableInitialization;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * Integration tests for how the parser resolves and initializes
 * primitive (PRIMITIVE) variable declarations, such as REAL, INT and BOOL.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class PrimitiveTypeIT extends ParserTestSupport {

    /**
     * Public static POJO grouping all parameters for the parameterized test.
     * Declared public to avoid visibility scope warnings when used in a public test method.
     */
    public static class PrimitiveTestData {
        public final String block;
        public final Source source;
        public final String typeName;
        public final Subtype subtype;
        public final String rawInitValue;
        public final String parsedInitValue;

        /**
         * Constructs a new test data combination.
         *
         * @param block           The variable block declaration (e.g., "VAR").
         * @param source          The source classification associated with the block.
         * @param typeName        The exact text representation of the primitive type.
         * @param subtype         The specific subtype mapped to the IEC 61131-7 standard.
         * @param rawInitValue    The literal initialization value as written in source code.
         * @param parsedInitValue The normalized initialization value expected after parsing.
         */
        public PrimitiveTestData(
                String block,
                Source source,
                String typeName,
                Subtype subtype,
                String rawInitValue,
                String parsedInitValue
        ) {
            this.block = block;
            this.source = source;
            this.typeName = typeName;
            this.subtype = subtype;
            this.rawInitValue = rawInitValue;
            this.parsedInitValue = parsedInitValue;
        }
    }

    private static final String[] BLOCKS = {"VAR", "VAR_INPUT", "VAR_OUTPUT"};
    private static final Source[] BLOCK_SOURCES = {Source.INTERNAL, Source.IN, Source.OUT};

    private static PrimitiveTestData[] typesFor(String block, Source source) {
        return new PrimitiveTestData[] {
                // Boolean type
                new PrimitiveTestData(block, source, "BOOL", Subtype.BOOL, "TRUE", "TRUE"),
                // Real types
                new PrimitiveTestData(block, source, "REAL", Subtype.REAL, "0.3e10", ".3e10"),
                new PrimitiveTestData(block, source, "LREAL", Subtype.LREAL, "0.3e10", ".3e10"),
                // Int types
                new PrimitiveTestData(block, source, "SINT", Subtype.SINT, "42", "42"),
                new PrimitiveTestData(block, source, "INT", Subtype.INT, "42", "42"),
                new PrimitiveTestData(block, source, "LINT", Subtype.LINT, "42", "42"),
                new PrimitiveTestData(block, source, "DINT", Subtype.DINT, "42", "42"),
                // UInt types
                new PrimitiveTestData(block, source, "USINT", Subtype.USINT, "42", "42"),
                new PrimitiveTestData(block, source, "UINT", Subtype.UINT, "42", "42"),
                new PrimitiveTestData(block, source, "ULINT", Subtype.ULINT, "42", "42"),
                new PrimitiveTestData(block, source, "UDINT", Subtype.UDINT, "42", "42"),
                // String types
                new PrimitiveTestData(block, source, "STRING", Subtype.STRING, "'string'", "'string'"),
                new PrimitiveTestData(block, source, "WSTRING", Subtype.WSTRING, "\"wstring\"", "\"wstring\"")
        };
    }

    /**
     * Cartesian product of variable blocks and primitive types.
     *
     * @return one {@link PrimitiveTestData} per (block, type) combination.
     */
    static Stream<PrimitiveTestData> providePrimitiveCombinations() {
        List<PrimitiveTestData> arguments = new ArrayList<>();
        for (int i = 0; i < BLOCKS.length; i++) {
            arguments.addAll(Arrays.asList(typesFor(BLOCKS[i], BLOCK_SOURCES[i])));
        }
        return arguments.stream();
    }

    /**
     * Integration test verifying that the compiler's parser correctly structures and populates
     * the symbol table for different primitive types and assignment combinations.
     *
     * @param data The test data payload containing combinations of blocks and types.
     * @throws Exception If parsing or validation encounters a fatal error.
     */
    @ParameterizedTest
    @MethodSource("providePrimitiveCombinations")
    public void Declaring_Uninitialized_Primitive_Variable_Uses_Default(PrimitiveTestData data) throws Exception {

        String sourceCode = "FUNCTION_BLOCK main\n"
                + data.block + "\n"
                + "    var1 : " + data.typeName + ";\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        // Apply the lambda factory to generate the default initialization linked to the symbol table
        Object defaultInitialization = Factory.createPrimitiveInitialization(st, data.subtype);

        // Assert symbol 'var1' without explicit initialization is registered properly
        assertSymbol(st, "MAIN#VAR1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(data.subtype)
                .use(Use.VARIABLE)
                .source(data.source)
                .initialValue(defaultInitialization)
                .build());
    }

    /**
     * Integration test verifying that the compiler's parser correctly structures and populates
     * the symbol table for different primitive types and assignment combinations.
     *
     * @param data The test data payload containing combinations of blocks and types.
     * @throws Exception If parsing or validation encounters a fatal error.
     */
    @ParameterizedTest
    @MethodSource("providePrimitiveCombinations")
    public void Declaring_Initialized_Primitive_Variable_Uses_Literal_Value(PrimitiveTestData data) throws Exception {

        String sourceCode = "FUNCTION_BLOCK main\n"
                + data.block + "\n"
                + "    var1 : " + data.typeName + " := " + data.rawInitValue + ";\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        // Assert symbol 'var' with explicit inline initialization is registered properly
        assertSymbol(st, "MAIN#VAR1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(data.subtype)
                .use(Use.VARIABLE)
                .source(data.source)
                .initialValue(new VariableInitialization(data.parsedInitValue))
                .build());
    }
}