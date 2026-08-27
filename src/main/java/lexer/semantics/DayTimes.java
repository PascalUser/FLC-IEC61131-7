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
@SuppressWarnings("SpellCheckingInspection")
public class DayTimes implements SemanticAnalyzer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public int analyze(LexicalContext currentContext) {
        String currentLexeme = currentContext.lexeme();

        try {
            LocalTime time = parse(currentLexeme);

            currentContext.symbolTable().putIfAbsent(currentLexeme,
                    new LexemeInfoBuilder()
                            .type(Type.SIMPLE)
                            .subtype(Subtype.TIME_OF_DAY)
                            .use(Use.LITERAL)
                            .initialValue(time)
                            .build()
            );
            return Parser.Lexer.TIME_LITERAL;

        } catch (DateTimeParseException e) {
            currentContext.diagnosticsHandler().add(
                new TimeOfDayOutOfRange(currentContext.line(), currentLexeme)
            );
            return Parser.Lexer.YYerror;
        }
    }

    static LocalTime parse(String lexeme) throws DateTimeParseException {
        return LocalTime.parse(lexeme, FORMATTER);
    }
}