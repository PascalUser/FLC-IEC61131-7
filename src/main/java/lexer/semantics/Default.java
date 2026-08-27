package lexer.semantics;

import parser.Parser.Lexer;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;

/**
 * Semantic analyzer for string literal fallback handling.
 * <p>
 * Used for STRING and WSTRING literals when no specific analyzer is matched.
 * Looks up the lexeme in the symbol table and returns the corresponding token.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class Default implements SemanticAnalyzer {
    private final Subtype subtype;

    public Default(Subtype subtype) {
        this.subtype = subtype;
    }

    @Override
    public int analyze(LexicalContext currentContext) {
        currentContext.symbolTable().putIfAbsent(currentContext.lexeme(), new LexemeInfoBuilder().build());
        return this.translate(this.subtype);
    }

    /**
     * Maps a subtype to its corresponding lexer token number.
     *
     * @param subtype the semantic subtype
     * @return the token identifier, or -1 if unknown
     */
    private int translate(Subtype subtype) {
        switch (subtype) {
            case STRING:
            case WSTRING:
                return Lexer.STRING_LITERAL;
            case TIME:
            case DATE:
            case TIME_OF_DAY:
            case DATE_AND_TIME:
                return Lexer.TIME_LITERAL;
            case BOOL:
            case SINT:
            case INT:
            case DINT:
            case LINT:
            case USINT:
            case UINT:
            case UDINT:
            case ULINT:
            case REAL:
            case LREAL:
            case BYTE:
            case WORD:
            case DWORD:
            case LWORD:
                return Lexer.NUMERIC_LITERAL;
            default:
                return -1;
        }
    }
}
