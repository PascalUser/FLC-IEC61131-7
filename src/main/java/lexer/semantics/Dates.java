package lexer.semantics;

import lexer.Lexer;
import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.DateOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

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
public class Dates implements SemanticAnalyzer {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("uuuu-MM-dd")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public int analyze(LexicalContext currentContext) {
        String currentLexeme = currentContext.lexeme();

        try {
            LocalDate date = parse(currentLexeme);

            currentContext.symbolTable().putIfAbsent(currentLexeme,
                    new LexemeInfoBuilder()
                            .type(Type.SIMPLE)
                            .subtype(Subtype.DATE)
                            .use(Use.LITERAL)
                            .initialValue(date)
                            .build()
            );
            return Parser.Lexer.TIME_LITERAL;

        } catch (DateTimeParseException e) {
            currentContext.diagnosticsHandler().add(
                new DateOutOfRange(currentContext.line(), currentLexeme)
            );
            return Lexer.YYerror;
        }
    }

    static LocalDate parse(String lexeme) throws DateTimeParseException {
        return LocalDate.parse(lexeme, FORMATTER);
    }
}