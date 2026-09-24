package utils;

import lexer.Lexer;
import parser.Parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

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

    protected static Map<String, Object> mapOf(Object... keyValues) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put((String) keyValues[i], keyValues[i + 1]);
        }
        return map;
    }
}