package lexer.semantics;

import lexer.Lexer;
import parser.Parser;
import utils.diagnostics.IntervalConstructionError;
import utils.diagnostics.IntervalOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;
import utils.builders.LexemeInfoBuilder;

import java.time.Duration;

/**
 * Semantic analyzer for TIME (interval) literals.
 * <p>
 * Verifies semantic restrictions for interval literals. The lexer already validates
 * the lexical format and preprocesses the lexeme (removes underscores, leading/trailing zeros).
 * </p>
 * <p>
 * IEC 61131-3 Restrictions:
 * <ul>
 *   <li>Only the highest-order non-zero magnitude may exceed its natural limit</li>
 *   <li>Magnitude limits: hours (≤23), minutes (≤59), seconds (≤59), milliseconds (≤999)</li>
 *   <li>Total duration must fit in signed 64-bit Long (in nanoseconds)</li>
 * </ul>
 * </p>
 * Stores {@link Duration} as initialValue (nanoseconds precision).
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public class Intervals implements SemanticAnalyzer {
    private static final double MAX_HOURS   = 23D;
    private static final double MAX_MINUTES = 59D;
    private static final double MAX_SECONDS = 59D;
    private static final double MAX_MILLIS  = 999D;

    private static final double MILLIS_PER_DAY    = 86400000D;
    private static final double MILLIS_PER_HOUR   = 3600000D;
    private static final double MILLIS_PER_MINUTE = 60000D;
    private static final double MILLIS_PER_SECOND = 1000D;

    private static final double NANOS_PER_MILLIS = 1_000_000D;

    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;
        StringBuilder numBuf = new StringBuilder();

        double totalNS = 0D;
        boolean isNegative = !lexeme.isEmpty() && lexeme.charAt(0) == '-';

        int startIdx = isNegative ? 1 : 0;
        boolean first = true;

        for (int i = startIdx; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);

            if (this.isUnit(currentChar)) {
                StringBuilder unitBuf = new StringBuilder();
                unitBuf.append(currentChar);

                if (this.nextIsAlsoUnit(lexeme, i)) {
                    unitBuf.append(lexeme.charAt(++i));
                }

                double number = Double.parseDouble(numBuf.toString());
                String unit = unitBuf.toString();

                // Verifies first restriction and notifies error
                if (this.checkIfMagnitudeOverflows(number, unit, first)) {
                    ctx.diagnosticsHandler.add(
                            new IntervalConstructionError(ctx.line, lexeme)
                    );
                    return new Result(null, Lexer.YYerror);
                }
                first = false;

                // Verifies second restriction and notifies error
                double increment = this.calculateIncrementIfInRange(totalNS, number, unit);
                if (increment == -1D) {
                    ctx.diagnosticsHandler.add(
                            new IntervalOutOfRange(ctx.line, lexeme)
                    );
                    return new Result(null, Lexer.YYerror);
                }
                totalNS += increment;
                numBuf.setLength(0);

            } else {
                numBuf.append(currentChar);
            }
        }

        long finalNanos = (long) (isNegative ? -totalNS : totalNS);
        Duration duration = Duration.ofNanos(finalNanos);

        ctx.symbolTable.putIfAbsent(
                lexeme,
                new LexemeInfoBuilder()
                        .type(Type.SIMPLE)
                        .subtype(Subtype.TIME)
                        .use(Use.LITERAL)
                        .initialValue(duration)
                        .build()
        );
        return new Result(lexeme, Parser.Lexer.TIME_LITERAL);
    }

    private boolean isUnit(char c) {
        return c != '.' && !Character.isDigit(c);
    }

    private boolean nextIsAlsoUnit(String lexeme, int currPos) {
        return (currPos + 1 < lexeme.length() && !Character.isDigit(lexeme.charAt(currPos + 1)));
    }

    private boolean checkIfMagnitudeOverflows(double number, String unit, boolean first) {
        if (first) return false;

        switch (unit) {
            case "H":
                return number > MAX_HOURS;
            case "M":
                return number > MAX_MINUTES;
            case "S":
                return number > MAX_SECONDS;
            default:
                return number > MAX_MILLIS;
        }
    }

    private double calculateIncrementIfInRange(double totalNS, double number, String unit) {
        double increment;

        switch (unit) {
            case "D":
                increment = NANOS_PER_MILLIS * MILLIS_PER_DAY * number;
                break;
            case "H":
                increment = NANOS_PER_MILLIS * MILLIS_PER_HOUR * number;
                break;
            case "M":
                increment = NANOS_PER_MILLIS * MILLIS_PER_MINUTE * number;
                break;
            case "S":
                increment = NANOS_PER_MILLIS * MILLIS_PER_SECOND * number;
                break;
            default:
                increment = NANOS_PER_MILLIS * number;
                break;
        }
        double possibleTotal = totalNS + increment;

        if (Double.isInfinite(possibleTotal) || possibleTotal > Long.MAX_VALUE) {
            return -1D;
        }
        return increment;
    }
}
