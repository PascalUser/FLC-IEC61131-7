package lexer.semantics;

import lexer.Lexer;
import lexer.ReservedWords;
import utils.*;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

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
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;
        Integer tokenNumber = ReservedWords.isReserved(lexeme);
        if (tokenNumber != null) {
            if (!tokenNumber.equals(Lexer.BOOLEAN_LITERAL)){
                return new Result(null, tokenNumber);
            }
            ctx.symbolTable.putIfAbsent(lexeme, new LexemeInfoBuilder()
                    .type(Type.SIMPLE)
                    .subtype(Subtype.BOOL)
                    .use(Use.LITERAL)
                    .initialValue(Boolean.valueOf(lexeme))
                    .build()
            );
            return new Result(lexeme, Lexer.BOOLEAN_LITERAL);
        }
        ctx.symbolTable.putIfAbsent(lexeme, new LexemeInfoBuilder().build());
        return new Result(lexeme, Lexer.IDENTIFIER);
    }
}