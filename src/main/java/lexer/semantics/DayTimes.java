package lexer.semantics;

import parser.Parser;
import utils.diagnostics.TimeOfDayOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;
import utils.builders.LexemeInfoBuilder;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Semantic analyzer for TIME_OF_DAY literals (HH:MM:SS[.fffffffff]).
 * <p>
 * Verifies time-of-day restrictions. The lexer already validates the lexical format.
 * </p>
 * <p>
 * Restrictions: hour 0-23, minute 0-59, second 0-59, optional nanosecond fraction.
 * Uses strict ISO_LOCAL_TIME parsing with ResolverStyle.STRICT.
 * </p>
 * Stores {@link LocalTime} as initialValue.
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class DayTimes implements SemanticAnalyzer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;

        try {
            LocalTime time = parse(lexeme);

            ctx.symbolTable.putIfAbsent(lexeme,
                    new LexemeInfoBuilder()
                            .type(Type.SIMPLE)
                            .subtype(Subtype.TIME_OF_DAY)
                            .use(Use.LITERAL)
                            .initialValue(time)
                            .build()
            );
            return new Result(lexeme, Parser.Lexer.TIME_LITERAL);

        } catch (DateTimeParseException e) {
            ctx.diagnosticsHandler.add(
                new TimeOfDayOutOfRange(ctx.line, lexeme)
            );
            return new Result(null, Parser.Lexer.YYerror);
        }
    }

    static LocalTime parse(String lexeme) throws DateTimeParseException {
        return LocalTime.parse(lexeme, FORMATTER);
    }
}