package compiler.lexer.semantics.numbers;

import org.jspecify.annotations.NonNull;
import compiler.repository.diagnostics.Diagnostic;
import compiler.repository.diagnostics.pinpointed.outofrange.RealOutOfRange;
import compiler.repository.enums.Subtype;

/**
 * Semantic analyzer for real (floating-point) numeric literals.
 * <p>
 * Parses decimal floating-point numbers and determines the appropriate
 * subtype (REAL or LREAL) based on the magnitude. Handles special values
 * like infinity and reports a warning for out-of-range values with fallback.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class Reals extends NumbersAnalyzer {
    private static class CorrectedInfo {
        public final String correctedLexeme;
        public final Object correctedValue;

        private CorrectedInfo(String lexeme, Object initialValue) {
            this.correctedLexeme = lexeme;
            this.correctedValue = initialValue;
        }
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        CorrectedInfo i = getCorrectedInfo(lexeme);
        return new ParsedValue(i.correctedLexeme, Subtype.LREAL, i.correctedValue);
    }

    /** {@inheritDoc} */
    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new RealOutOfRange(line, lexeme, getCorrectedInfo(lexeme).correctedLexeme);
    }

    /**
     * Determines the floating-point subtype based on lexeme magnitude.
     *
     * @param value the parsed double lexeme
     * @return REAL if within float range, LREAL otherwise
     */
    private static Subtype getRange(double value) {
        if (Double.isInfinite(value)) return Subtype.UNKNOWN;
        double abs = Math.abs(value);
        if (abs <= Float.MAX_VALUE) return Subtype.REAL;
        return Subtype.LREAL;
    }

    private static CorrectedInfo getCorrectedInfo(String lexeme) {
        boolean isNegative = lexeme.startsWith("-");
        double initialValue = isNegative ? -Double.MAX_VALUE : Double.MAX_VALUE;
        return new CorrectedInfo(String.valueOf(initialValue), initialValue);
    }
}
