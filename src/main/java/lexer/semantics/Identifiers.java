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
    public int analyze(LexicalContext currentContext) {
        String currentLexeme = currentContext.lexeme();
        Integer tokenNumber = ReservedWords.isReserved(currentLexeme);
        if (tokenNumber != null) {
            if (!tokenNumber.equals(Lexer.BOOLEAN_LITERAL)){
                return tokenNumber;
            }
            currentContext.symbolTable().putIfAbsent(currentLexeme, new LexemeInfoBuilder()
                    .type(Type.SIMPLE)
                    .subtype(Subtype.BOOL)
                    .use(Use.LITERAL)
                    .initialValue(Boolean.valueOf(currentLexeme))
                    .build()
            );
            return Lexer.BOOLEAN_LITERAL;
        }
        currentContext.symbolTable().putIfAbsent(currentLexeme, new LexemeInfoBuilder().build());
        return Lexer.IDENTIFIER;
    }
}