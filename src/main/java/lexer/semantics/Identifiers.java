package lexer.semantics;

import lexer.ReservedWords;
import parser.Parser;
import utils.*;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;

public class Identifiers implements SemanticAnalyzer {
    private final SymbolTable symbolTable;

    public Identifiers(SymbolTable initSymbols) {
        this.symbolTable = initSymbols;
    }

    public Result analyze(String lexeme, Subtype subtype) {
        Integer token = ReservedWords.isReserved(lexeme);
        if (token != null) {
            return new Result(null, token);
        }
        if (this.symbolTable.get(lexeme) == null) {
            this.symbolTable.put(lexeme, new LexemeInfoBuilder().build());
        }
        return new Result(lexeme, Parser.Lexer.IDENTIFIER);
    }
}
