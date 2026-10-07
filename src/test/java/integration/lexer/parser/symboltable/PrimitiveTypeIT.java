package integration.lexer.parser.symboltable;

import utils.ParserTestSupport;

import parser.initializations.BooleanInitialization;
import parser.initializations.RealInitialization;
import parser.initializations.VariableInitialization;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.LexemeInfoComparator;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        public final Function<SymbolTable, Object> initFactory;

        /**
         * Constructs a new test data combination.
         *
         * @param block           The variable block declaration (e.g., "VAR").
         * @param source          The source classification associated with the block.
         * @param typeName        The exact text representation of the primitive type.
         * @param subtype         The specific subtype mapped to the IEC 61131-7 standard.
         * @param rawInitValue    The literal initialization value as written in source code.
         * @param parsedInitValue The normalized initialization value expected after parsing.
         * @param initFactory     A factory lambda to instantiate the default initialization dynamically.
         */
        public PrimitiveTestData(
                String block,
                Source source,
                String typeName,
                Subtype subtype,
                String rawInitValue,
                String parsedInitValue,
                Function<SymbolTable, Object> initFactory
        ) {
            this.block = block;
            this.source = source;
            this.typeName = typeName;
            this.subtype = subtype;
            this.rawInitValue = rawInitValue;
            this.parsedInitValue = parsedInitValue;
            this.initFactory = initFactory;
        }
    }

    /**
     * Helper class to strongly type the variable block configurations,
     * avoiding Object arrays and unchecked casts.
     */
    private static class BlockConfig {
        final String block;
        final Source source;

        BlockConfig(String block, Source source) {
            this.block = block;
            this.source = source;
        }
    }

    /**
     * Helper class to strongly type the data type configurations,
     * allowing the Function lambda to be stored safely without type erasure warnings.
     */
    private static class TypeConfig {
        final String typeName;
        final Subtype subtype;
        final String rawInitValue;
        final String parsedInitValue;
        final Function<SymbolTable, Object> initFactory;

        TypeConfig(String typeName, Subtype subtype, String rawInitValue, String parsedInitValue, Function<SymbolTable, Object> initFactory) {
            this.typeName = typeName;
            this.subtype = subtype;
            this.rawInitValue = rawInitValue;
            this.parsedInitValue = parsedInitValue;
            this.initFactory = initFactory;
        }
    }

    /**
     * Generates a Cartesian product of variable block declarations and primitive types
     * to test all combinations systematically.
     *
     * @return A stream of strongly-typed PrimitiveTestData objects.
     */
    static Stream<PrimitiveTestData> providePrimitiveCombinations() {
        List<PrimitiveTestData> arguments = new ArrayList<>();

        // 1. Define the variable block declaration targets
        List<BlockConfig> blocks = Arrays.asList(
                new BlockConfig("VAR", Source.INTERNAL),
                new BlockConfig("VAR_INPUT", Source.IN),
                new BlockConfig("VAR_OUTPUT", Source.OUT)
        );

        // 2. Define the primitive types to test
        List<TypeConfig> types = Arrays.asList(
                new TypeConfig("REAL", Subtype.REAL, "0.3e10", ".3e10", RealInitialization::new),
                new TypeConfig("BOOL", Subtype.BOOL, "TRUE", "TRUE", BooleanInitialization::new)
                // new TypeConfig("INT", Subtype.INT, "42", "42", IntInitialization::new),
        );

        // 3. Construct the Cartesian product
        for (BlockConfig b : blocks) {
            for (TypeConfig t : types) {
                arguments.add(new PrimitiveTestData(
                        b.block, b.source,
                        t.typeName, t.subtype,
                        t.rawInitValue, t.parsedInitValue,
                        t.initFactory
                ));
            }
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
    public void declaring_uninitialized_primitive_variable_uses_default_initialization(PrimitiveTestData data) throws Exception {

        String sourceCode = "FUNCTION_BLOCK main\n"
                + data.block + "\n"
                + "    var1 : " + data.typeName + ";\n"
                + "    var2 : " + data.typeName + " := " + data.rawInitValue + ";\n"
                + "END_VAR\n"
                + "END_FUNCTION_BLOCK";

        SymbolTable st = parse(sourceCode);
        List<String> diffs;

        // Apply the lambda factory to generate the default initialization linked to the symbol table
        Object defaultInitialization = data.initFactory.apply(st);

        // Assert symbol 'var1' without explicit initialization is registered properly
        diffs = LexemeInfoComparator.compare(st, "MAIN#VAR1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(data.subtype)
                .use(Use.VARIABLE)
                .source(data.source)
                .initialValue(defaultInitialization)
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());

        // Assert symbol 'var2' with explicit inline initialization is registered properly
        diffs = LexemeInfoComparator.compare(st, "MAIN#VAR2", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(data.subtype)
                .use(Use.VARIABLE)
                .source(data.source)
                .initialValue(new VariableInitialization(data.parsedInitValue))
                .build());
        assertTrue(diffs.isEmpty(), diffs.toString());
    }

    @ParameterizedTest
    @MethodSource("providePrimitiveCombinations")
    public void declaring_initialized_primitive_variable_stores_explicit_value(PrimitiveTestData data) throws Exception {
        // This test is covered by the same parameterized method above
        // Keeping it here for explicit naming per Khorikov convention
        declaring_uninitialized_primitive_variable_uses_default_initialization(data);
    }
}