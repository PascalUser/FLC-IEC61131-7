package lexer.semantics;

import utils.DiagnosticsHandler;
import utils.SymbolTable;

/**
 * Interface for semantic analysis of lexemes during lexical scanning.
 * <p>
 * Each implementation handles a specific category of tokens (identifiers,
 * numeric literals, etc.) and is responsible for validating the lexeme,
 * updating the symbol table, and reporting diagnostics.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface SemanticAnalyzer {

    /**
     * Result of semantic analysis containing the result lexeme and token number.
     */
    final class Result {
        private final String lexeme;
        private final int tokenNumber;

        public Result(String lexeme, int tokenNumber) {
            this.lexeme = lexeme;
            this.tokenNumber = tokenNumber;
        }

        public String lexeme() {
            return lexeme;
        }

        public int tokenNumber() {
            return tokenNumber;
        }
    }

    /**
     * Context provided to the analyzer during lexical analysis.
     */
    final class LexicalContext {
        private final String lexeme;
        private final int line;
        private final SymbolTable symbolTable;
        private final DiagnosticsHandler diagnosticsHandler;

        public LexicalContext(String lexeme, int line, SymbolTable symbolTable, DiagnosticsHandler diagnosticsHandler) {
            this.lexeme = lexeme;
            this.line = line;
            this.symbolTable = symbolTable;
            this.diagnosticsHandler = diagnosticsHandler;
        }

        public String lexeme() {
            return lexeme;
        }

        public int line() {
            return line;
        }

        public SymbolTable symbolTable() {
            return symbolTable;
        }

        public DiagnosticsHandler diagnosticsHandler() {
            return diagnosticsHandler;
        }
    }

    /**
     * Performs semantic analysis on the given lexical context.
     *
     * @param currentContext the context containing lexeme and environment
     * @return result with processed lexeme and token number
     */
    Result analyze(LexicalContext currentContext);
}