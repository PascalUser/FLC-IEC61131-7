package lexer.semantics;

import lexer.Lexer;
import parser.Parser;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.DateOutOfRange;
import utils.enums.Subtype;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Semantic analyzer for DATE literals (YYYY-MM-DD).
 * <p>
 * Verifies calendar date restrictions. The lexer already validates the lexical format.
 * </p>
 * <p>
 * Restrictions: valid calendar date including leap years (e.g., 2024-02-29 valid, 2023-02-29 invalid).
 * Uses strict ISO_LOCAL_DATE parsing with ResolverStyle.STRICT.
 * </p>
 * Stores {@link LocalDate} as initialValue.
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class Dates implements SemanticAnalyzer {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("uuuu-MM-dd")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;

        try {
            LocalDate date = parse(lexeme);

            LexemeInfoBuilder builder = new LexemeInfoBuilder();
            Director.makeLiteral(builder);
            ctx.symbolTable.putIfAbsent(lexeme, builder
                    .subtype(Subtype.DATE)
                    .initialValue(date)
                    .build()
            );
            return new Result(lexeme, Parser.Lexer.TIME_LITERAL);

        } catch (DateTimeParseException e) {
            ctx.diagnosticsHandler.add(
                new DateOutOfRange(ctx.line, lexeme)
            );
            return new Result(null, Lexer.YYerror);
        }
    }

    static LocalDate parse(String lexeme) throws DateTimeParseException {
        return LocalDate.parse(lexeme, FORMATTER);
    }
}