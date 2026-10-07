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
     * Holds the processed lexeme and token number produced by an analyzer.
     *
     * @author Matías Ortiz
     * @author Victoriano Etcheverría
     * @since 1.0-SNAPSHOT
     * @version 1.0-SNAPSHOT
     */
    final class Result {
        /** Processed lexeme returned to the scanner. */
        public final String lexeme;
        /** Token code returned to the scanner. */
        public final int token;

        /**
         * Creates a result from the processed lexeme and token code.
         *
         * @param lexeme the lexeme produced by semantic analysis
         * @param token the token code produced by semantic analysis
         */
        public Result(String lexeme, int token) {
            this.lexeme = lexeme;
            this.token = token;
        }
    }

    /**
     * Holds the scanner input and services supplied to a semantic analyzer.
     *
     * @author Matías Ortiz
     * @author Victoriano Etcheverría
     * @since 1.0-SNAPSHOT
     * @version 1.0-SNAPSHOT
     */
    final class LexicalContext {
        /** Lexeme after lexical preprocessing. */
        public final String preprocessedLexeme;
        /** One-based source line of the lexeme. */
        public final int line;
        /** Symbol table available to the analyzer. */
        public final SymbolTable symbolTable;
        /** Collector for emitted diagnostics. */
        public final DiagnosticsHandler diagnosticsHandler;

        /**
         * Creates a lexical context for a preprocessed lexeme.
         *
         * @param preprocessedLexeme the input lexeme after preprocessing
         * @param line the one-based source line
         * @param symbolTable the symbol table available to the analyzer
         * @param diagnosticsHandler the collector for reported diagnostics
         */
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