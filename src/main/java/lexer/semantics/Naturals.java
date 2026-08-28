package lexer.semantics;

import org.jspecify.annotations.NonNull;
import utils.diagnostics.Diagnostic;
import utils.diagnostics.NaturalOutOfRange;
import utils.enums.Subtype;

import java.math.BigInteger;

/**
 * Semantic analyzer for natural (unsigned decimal) numeric literals.
 * <p>
 * Parses unsigned decimal integers and determines the appropriate subtype
 * (USINT, UINT, UDINT, ULINT) based on the lexeme range. Reports a warning
 * if the lexeme exceeds 2^64 - 1 and uses fallback value.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Naturals extends NumbersAnalyzer {
    // Final member to avoid RAM out of space
    private static final int MAX_CONSTANT_LENGTH = 20;

    private static final BigInteger MAX_USINT = BigInteger.valueOf(255);
    private static final BigInteger MAX_UINT  = BigInteger.valueOf(65535);
    private static final BigInteger MAX_UDINT = new BigInteger("4294967295");
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615");

    @Override
    protected ParsedValue parse(String lexeme) {
        BigInteger initialValue = (lexeme.length() <= MAX_CONSTANT_LENGTH) ? new BigInteger(lexeme) : null;
        Subtype subtype = (initialValue != null) ? getRange(initialValue) : Subtype.UNKNOWN;
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        lexeme = MAX_ULINT.toString();
        Subtype subtype = Subtype.ULINT;
        return new ParsedValue(lexeme, subtype, MAX_ULINT);
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new NaturalOutOfRange(line, lexeme);
    }

    /**
     * Determines the smallest unsigned integer subtype that can hold the lexeme.
     *
     * @param value the parsed BigInteger lexeme
     * @return the appropriate subtype
     */
    private Subtype getRange(BigInteger value) {
        if (value.compareTo(MAX_USINT) <= 0) return Subtype.USINT;
        if (value.compareTo(MAX_UINT)  <= 0) return Subtype.UINT;
        if (value.compareTo(MAX_UDINT) <= 0) return Subtype.UDINT;
        if (value.compareTo(MAX_ULINT) <= 0) return Subtype.ULINT;
        return Subtype.UNKNOWN;
    }
}
