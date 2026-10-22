package utils;

import lexer.Lexer;
import parser.Parser;
import parser.initializations.Initialization;
import parser.initializations.leafs.DefaultInitialization;
import utils.builders.LexemeInfoBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Base class for parser / symbol-table integration tests.
 * <p>
 * Holds the parsing entry points and the assertion helpers that are generic across declaration kinds.
 * Helpers specific to one kind belong in the test class that owns that kind.
 * </p>
 */
public abstract class ParserTestSupport {

    protected SymbolTable parse(Reader reader) throws Exception {
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnosticHandler = new DiagnosticsHandler();

        Lexer lexer = new Lexer(reader, symbolTable, diagnosticHandler);
        Parser parser = new Parser(lexer, symbolTable);

        assertTrue(parser.parse(), "Parsing should succeed");
        assertFalse(diagnosticHandler.hasErrors());
        return symbolTable;
    }

    protected SymbolTable parse(String sourceCode) throws Exception {
        return this.parse(new StringReader(sourceCode));
    }

    protected SymbolTable parse(SourceCode sourceCode) throws Exception {
        return this.parse(sourceCode.build());
    }

    /**
     * Asserts that the compiler rejects the program: either the parser fails or a fatal diagnostic is
     * recorded. An uncaught exception (e.g. a NullPointerException inside a semantic action) fails the
     * test on purpose: invalid input must produce a diagnostic, not a crash.
     *
     * @param sourceCode the invalid program
     * @throws Exception if the lexer fails with an I/O error
     */
    protected void assertRejected(String sourceCode) throws Exception {
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        Parser parser = new Parser(new Lexer(new StringReader(sourceCode), symbolTable, diagnostics), symbolTable);

        boolean accepted = parser.parse();

        assertTrue(!accepted || diagnostics.hasErrors(), "Expected the program to be rejected:\n" + sourceCode);
    }

    protected void assertRejected(SourceCode sourceCode) throws Exception {
        this.assertRejected(sourceCode.build());
    }

    /**
     * Asserts that {@code key} is registered with exactly the {@code expected} metadata, failing with the
     * list of field-level diffs.
     *
     * @param symbolTable the table produced by {@link #parse(String)}
     * @param key         the (mangled) symbol name, e.g. {@code "MAIN#VAR1"}
     * @param expected    the metadata the symbol must have
     */
    protected static void assertSymbol(SymbolTable symbolTable, String key, LexemeInfo expected) {
        List<String> diffs = LexemeInfoComparator.compare(symbolTable, key, expected);
        assertTrue(diffs.isEmpty(), key + ": " + diffs);
    }

    /**
     * Asserts every field of the symbol except its initial value, and checks the initial value by its
     * textual form ({@code variableValue()}) instead of by rebuilding it with production code.
     *
     * @param symbolTable        the table produced by {@link #parse(String)}
     * @param key                the (mangled) symbol name
     * @param expectedWithoutValue the expected metadata; its initial value is ignored
     * @param expectedValueText  the expected {@code variableValue()} of the initial value
     */
    protected static void assertSymbolWithValue(
            SymbolTable symbolTable, String key, LexemeInfoBuilder expectedWithoutValue, String expectedValueText
    ) {
        LexemeInfo actual = symbolTable.get(key);
        assertNotNull(actual, key + " was not published");
        assertInstanceOf(
                Initialization.class, actual.initialValue,
                key + ": initial value is not an Initialization: " + actual.initialValue
        );
        assertEquals(
                expectedValueText,
                ((Initialization) actual.initialValue).variableValue(),
                key + ": initial value"
        );
        assertSymbol(symbolTable, key, expectedWithoutValue.initialValue(actual.initialValue).build());
    }

    /** Default REAL initialization built on a throw-away table, so it never pollutes the table under test. */
    protected static Initialization realDefault() {
        return DefaultInitialization.real(new SymbolTable());
    }

    protected static Initialization boolDefault() {
        return DefaultInitialization.bool(new SymbolTable());
    }

    protected static Initialization intDefault() {
        return DefaultInitialization.integer(new SymbolTable());
    }
}
