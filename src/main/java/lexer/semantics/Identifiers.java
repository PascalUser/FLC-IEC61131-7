package lexer.semantics;

import lexer.ReservedWords;
import parser.Parser;
import utils.*;
import utils.builders.LexemeInfoBuilder;

/**
 * Semantic analyzer for identifiers and reserved words.
 * <p>
 * Checks if the lexeme is a reserved keyword. If not, treats it as an
 * identifier and ensures it exists in the symbol table.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Identifiers implements SemanticAnalyzer {
    @Override
    public Result analyze(LexicalContext currentContext) {
        String currentLexeme = currentContext.lexeme();
        Integer tokenNumber = ReservedWords.isReserved(currentLexeme);
        if (tokenNumber != null) {
            return new Result(null, tokenNumber);
        }
        if (currentContext.symbolTable().get(currentLexeme) == null) {
            currentContext.symbolTable().put(currentLexeme, new LexemeInfoBuilder().build());
        }
        return new Result(currentLexeme, Parser.Lexer.IDENTIFIER);
    }
}