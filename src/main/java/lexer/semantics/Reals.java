package lexer.semantics;

import org.jspecify.annotations.NonNull;
import utils.diagnostics.Diagnostic;
import utils.diagnostics.RealOutOfRange;
import utils.enums.Subtype;

public class Reals extends NumericAnalyzer {

    @Override
    protected ParsedValue parse(String lexeme) {
        Double initialValue;
        Subtype subtype;
        try {
            initialValue = Double.parseDouble(lexeme);
            subtype = getRange(initialValue);
        } catch (NumberFormatException e) {
            initialValue = null;
            subtype = Subtype.UNKNOWN;
        }
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    private Subtype getRange(double value) {
        if (Double.isInfinite(value)) return Subtype.UNKNOWN;
        double abs = Math.abs(value);
        if (abs <= Float.MAX_VALUE) return Subtype.REAL;
        return Subtype.LREAL;
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        boolean isNegative = lexeme.startsWith("-");
        double initialValue = isNegative ? -Double.MAX_VALUE : Double.MAX_VALUE;
        lexeme = Double.toString(initialValue);
        Subtype subtype = Subtype.LREAL;
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new RealOutOfRange(line, lexeme);
    }
}