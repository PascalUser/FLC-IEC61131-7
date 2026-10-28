package utils;

import ast.AbstractSyntaxTree;
import lexer.Lexer;
import parser.Parser;
import parser.initializations.Factory;
import parser.initializations.Initialization;
import parser.initializations.leafs.DefaultInitialization;
import utils.enums.Subtype;

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

    protected ParsingResults parse(Reader reader) throws Exception {
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnosticHandler = new DiagnosticsHandler();

        Lexer lexer = new Lexer(reader, symbolTable, diagnosticHandler);
        Parser parser = new Parser(lexer, symbolTable);

        assertTrue(parser.parse(), "Parsing should succeed");
        assertFalse(diagnosticHandler.hasErrors());
        AbstractSyntaxTree ast = parser.getAST();
        assertNotNull(ast, "Parser should build an AST");
        return new ParsingResults(symbolTable, ast, parser.getInterfaceVariables());
    }

    protected ParsingResults parse(SourceCode sourceCode) throws Exception {
        System.out.println(sourceCode.build());
        return this.parse(new StringReader(sourceCode.build()));
    }

    /**
     * Asserts that the compiler rejects the program: either the parser fails or a fatal diagnostic is
     * recorded. An uncaught exception (e.g. a NullPointerException inside a semantic action) fails the
     * test on purpose: invalid input must produce a diagnostic, not a crash.
     *
     * @param reader program reader of the invalid program
     * @throws Exception if the lexer fails with an I/O error
     */
    protected void assertRejected(Reader reader) throws Exception {
        SymbolTable symbolTable = new SymbolTable();
        DiagnosticsHandler diagnosticHandler = new DiagnosticsHandler();

        Lexer lexer = new Lexer(reader, symbolTable, diagnosticHandler);
        Parser parser = new Parser(lexer, symbolTable);

        boolean accepted = parser.parse();
        boolean mustBeTrue = !accepted || diagnosticHandler.hasErrors();
        assertTrue(mustBeTrue, "Expected the program to be rejected:\n" + reader.toString());
    }

    protected void assertRejected(SourceCode sourceCode) throws Exception {
        this.assertRejected(new StringReader(sourceCode.build()));
    }

    /**
     * Asserts that {@code key} is registered with exactly the {@code expected} metadata, failing with the
     * list of field-level diffs.
     *
     * @param symbolTable   the table produced by {@link #parse(SourceCode)}
     * @param symbol        the (mangled) symbol name, e.g. {@code "MAIN#VAR1"}
     * @param expectedInfo  the metadata the symbol must have
     */
    protected static void assertSymbol(SymbolTable symbolTable, String symbol, LexemeInfo expectedInfo) {
        List<String> diffs = LexemeInfoComparator.compare(symbolTable, symbol, expectedInfo);
        assertTrue(diffs.isEmpty(), symbol + ": " + diffs);
    }

    protected static Initialization boolDefault() {
        return DefaultInitialization.bool(new SymbolTable());
    }

    protected static Initialization intDefault() {
        return DefaultInitialization.integer(new SymbolTable());
    }

    protected static Initialization realDefault() {
        return DefaultInitialization.real(new SymbolTable());
    }

    protected static Initialization stringDefault() {
        return DefaultInitialization.string(new SymbolTable());
    }

    protected static Initialization defaultValue(Subtype subtype) {
        return Factory.createPrimitiveInitialization(new SymbolTable(), subtype);
    }



}
