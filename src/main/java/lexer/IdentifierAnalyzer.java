package lexer;

import parser.Parser;
import utils.*;

public class IdentifierAnalyzer implements SemanticAnalyzer {
    private final SymbolTable symbolTable;

    public IdentifierAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Result analyze(String lexeme, ElementaryType type) {
        LexemeInfo info = this.symbolTable.get(lexeme.toUpperCase());
        if (info != null && info.use.equals(Use.RESERVED)) {
            return new Result(null, );
        }
        if (this.symbolTable.get(lexeme) == null) {
            this.symbolTable.put(lexeme, new LexemeInfo());
        }
        return new Result(lexeme, Parser.Lexer.IDENTIFIER);
    }
}
