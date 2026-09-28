package lexer.semantics.strings;

import lexer.semantics.SemanticAnalyzer;
import parser.Parser;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;
import utils.diagnostics.StringLengthWarning;

/**
 * Abstract base analyzer for string literals (STRING and WSTRING).
 * <p>
 * Handles string content extraction, length validation (max 255 chars per IEC 61131-7),
 * and warning generation for oversized literals. Subclasses define the specific subtype.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see Strings
 * @see WStrings
 */
public abstract class StringsAnalyzer implements SemanticAnalyzer {

    private static final int MAX_STRING_LENGTH = 255;

    /**
     * Returns the string subtype (STRING or WSTRING).
     *
     * @return the subtype
     */
    protected abstract Subtype getSubtype();

    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;
        String content = lexeme.substring(1, lexeme.length() - 1);

        String canonicalContent = content;
        if (content.length() > MAX_STRING_LENGTH) {
            canonicalContent = content.substring(0, MAX_STRING_LENGTH);
            ctx.diagnosticsHandler.add(new StringLengthWarning(
                    ctx.line,
                    getSubtype() + " literal exceeds maximum length of " + MAX_STRING_LENGTH + " characters. Truncated."
            ));
            lexeme = lexeme.charAt(0) + canonicalContent + lexeme.charAt(lexeme.length() - 1);
        }

        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        Director.makeLiteral(builder);
        ctx.symbolTable.putIfAbsent(lexeme, builder
                .subtype(this.getSubtype())
                .initialValue(canonicalContent)
                .build()
        );
        return new Result(lexeme, Parser.Lexer.STRING_LITERAL);
    }
}