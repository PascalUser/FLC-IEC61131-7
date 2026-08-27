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
     * @return the token number on success, or {@link Lexer#YYerror} on semantic error
     */
    int analyze(LexicalContext currentContext);
}