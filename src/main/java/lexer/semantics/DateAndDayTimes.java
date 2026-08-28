package lexer.semantics;

import lexer.Lexer;
import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.DateAndTimeOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Semantic analyzer for DATE_AND_TIME literals (YYYY-MM-DD-HH:MM:SS[.fffffffff]).
 * <p>
 * Verifies combined date and time restrictions by delegating component parsing
 * to {@link Dates} and {@link DayTimes}.
 * </p>
 * Stores {@link LocalDateTime} as initialValue.
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class DateAndDayTimes implements SemanticAnalyzer {

    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;

        // Separate date component (10 chars) from time component after the 3rd hyphen
        if (lexeme.length() < 19 || lexeme.charAt(10) != '-') {
            ctx.diagnosticsHandler.add(new DateAndTimeOutOfRange(ctx.line, lexeme));
            return new Result(null, Lexer.YYerror);
        }

        String datePart = lexeme.substring(0, 10);
        String timePart = lexeme.substring(11);

        try {
            // Reuses validation logic from Dates and DayTimes
            LocalDate date = Dates.parse(datePart);
            LocalTime time = DayTimes.parse(timePart);
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            ctx.symbolTable.putIfAbsent(lexeme,
                    new LexemeInfoBuilder()
                            .type(Type.SIMPLE)
                            .subtype(Subtype.DATE_AND_TIME)
                            .use(Use.LITERAL)
                            .initialValue(dateTime)
                            .build()
            );
            return new Result(lexeme, Parser.Lexer.TIME_LITERAL);

        } catch (DateTimeParseException e) {
            ctx.diagnosticsHandler.add(new DateAndTimeOutOfRange(ctx.line, lexeme));
            return new Result(null, Lexer.YYerror);
        }
    }
}