package utils;

import lexer.Lexer;
import parser.Parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Base class for parser / symbol-table integration tests.
 * <p>
 * Holds the parsing entry point and the assertion helpers that are generic
 * across identifier kinds (plain variables, subrange types, custom-typed
 * variables). Helpers specific to a single kind (e.g. struct field/type
 * assertions) belong in the subclass that owns that kind, not here — this
 * class should stay kind-agnostic.
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

    /**
     * Asserts that {@code key} is registered in {@code symbolTable} with exactly
     * the {@code expected} metadata, failing with the list of field-level diffs.
     *
     * @param symbolTable the table produced by {@link #parse(String)}
     * @param key         the (mangled) symbol name, e.g. {@code "MAIN#VAR1"}
     * @param expected    the metadata the symbol must have
     */
    protected static void assertSymbol(SymbolTable symbolTable, String key, LexemeInfo expected) {
        List<String> diffs = LexemeInfoComparator.compare(symbolTable, key, expected);
        assertTrue(diffs.isEmpty(), key + ": " + diffs);
    }
}
