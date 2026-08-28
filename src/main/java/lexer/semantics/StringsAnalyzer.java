package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;
import utils.diagnostics.StringLengthWarning;

public abstract class StringsAnalyzer implements SemanticAnalyzer {

    private static final int MAX_STRING_LENGTH = 255;

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

        ctx.symbolTable.putIfAbsent(
                lexeme,
                new LexemeInfoBuilder()
                        .type(Type.SIMPLE)
                        .subtype(this.getSubtype())
                        .use(Use.LITERAL)
                        .source(Source.UNKNOWN)
                        .initialValue(canonicalContent)
                        .build()
        );

        return new Result(lexeme, Parser.Lexer.STRING_LITERAL);
    }
}