package lexer.semantics;

import utils.DiagnosticsHandler;
import utils.SymbolTable;

public interface SemanticAnalyzer {
    record Result (
        String value,
        int tokenNumber
    ) {}

    record LexicalContext(
        String lexeme,
        int line,
        SymbolTable symbolTable,
        DiagnosticsHandler diagnosticsHandler
    ) {}

    Result analyze(LexicalContext lexicalContext);
}