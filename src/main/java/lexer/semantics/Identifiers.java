package lexer.semantics;

import lexer.ReservedWords;
import parser.Parser;
import utils.*;
import utils.builders.LexemeInfoBuilder;

public class Identifiers implements SemanticAnalyzer {
    public Result analyze(LexicalContext lc) {
        Integer token = ReservedWords.isReserved(lc.lexeme());
        if (token != null) {
            return new Result(null, token);
        }
        if (lc.symbolTable().get(lc.lexeme()) == null) {
            lc.symbolTable().put(lc.lexeme(), new LexemeInfoBuilder().build());
        }
        return new Result(lc.lexeme(), Parser.Lexer.IDENTIFIER);
    }
}