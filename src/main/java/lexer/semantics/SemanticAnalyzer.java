package lexer.semantics;

import lexer.Lexer;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

/**
 * Interface for semantic analysis of lexemes during lexical scanning.
 * <p>
 * Each implementation handles a specific category of tokens (identifiers,
 * numeric literals, date/time literals, etc.) and is responsible for
 * validating the lexeme, updating the symbol table, and reporting
 * diagnostics through the handler.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface SemanticAnalyzer {

    /**
     * Result of semantic analysis containing the processed lexeme and token number.
     */
    final class Result {
        public final String lexeme;
        public final int token;

        public Result(String lexeme, int token) {
            this.lexeme = lexeme;
            this.token = token;
        }
    }

    /**
     * Context provided to the analyzer during lexical analysis.
     */
    final class LexicalContext {
        public final String preprocessedLexeme;
        public final int line;
        public final SymbolTable symbolTable;
        public final DiagnosticsHandler diagnosticsHandler;

        public LexicalContext(
                String preprocessedLexeme,
                int line,
                SymbolTable symbolTable,
                DiagnosticsHandler diagnosticsHandler
        ) {
            this.preprocessedLexeme = preprocessedLexeme;
            this.line = line;
            this.symbolTable = symbolTable;
            this.diagnosticsHandler = diagnosticsHandler;
        }
    }

    /**
     * Performs semantic analysis on the given lexical context.
     *
     * @param currentContext the context containing lexeme and environment
     * @return the token number on success, or {@link Lexer#YYerror} on semantic error
     */
    Result analyze(LexicalContext currentContext);
}