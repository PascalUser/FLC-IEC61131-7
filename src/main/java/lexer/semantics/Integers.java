package lexer.semantics;
import org.jspecify.annotations.NonNull;
import utils.diagnostics.Diagnostic;
import utils.diagnostics.IntegerOutOfRange;
import utils.enums.Subtype;


public class Integers extends NumericAnalyzer {
    private static final long MIN_SINT = -128L;
    private static final long MAX_SINT =  127L;
    private static final long MIN_INT = -32768L;
    private static final long MAX_INT =  32767L;
    private static final long MIN_DINT = -2147483648L;
    private static final long MAX_DINT =  2147483647L;
    private static final long MIN_LINT = Long.MIN_VALUE;
    private static final long MAX_LINT = Long.MAX_VALUE;

    @Override
    protected ParsedValue parse(String lexeme) {
        Long initialValue;
        Subtype subtype;
        try {
            initialValue =  Long.parseLong(lexeme);
            subtype = getRange(initialValue);
        } catch (NumberFormatException e) {
            initialValue = null;
            subtype = Subtype.UNKNOWN;
        }
        return new NumericAnalyzer.ParsedValue(lexeme, subtype, initialValue);
    }

    private Subtype getRange(long value) {
        if (value >= MIN_SINT && value <= MAX_SINT) return Subtype.SINT;
        if (value >= MIN_INT  && value <= MAX_INT)  return Subtype.INT;
        if (value >= MIN_DINT && value <= MAX_DINT) return Subtype.DINT;
        return Subtype.LINT;
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        boolean isNegative = lexeme.startsWith("-");
        long initialValue = isNegative ? MIN_LINT : MAX_LINT;
        lexeme = Long.toString(initialValue);
        Subtype subtype = Subtype.LINT;
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new IntegerOutOfRange(line, lexeme);
    }
}