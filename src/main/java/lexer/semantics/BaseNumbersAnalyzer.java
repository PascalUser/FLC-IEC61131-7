package lexer.semantics;
import org.jspecify.annotations.NonNull;
import utils.enums.Subtype;
import java.math.BigInteger;

/**
 * Abstract analyzer for based numeric literals (binary, octal, hexadecimal).
 * <p>
 * Extends {@link NumbersAnalyzer} to handle literals with explicit base prefixes.
 * Determines the appropriate subtype based on the number of digits and
 * provides error correction by clamping to the maximum representable lexeme.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseNumbersAnalyzer extends NumbersAnalyzer {
    private static final BigInteger MAX_USINT = BigInteger.valueOf(255);
    private static final BigInteger MAX_UINT  = BigInteger.valueOf(65535);
    private static final BigInteger MAX_UDINT = new BigInteger("4294967295");
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615");

    @Override
    protected ParsedValue parse(String lexeme) {
        final int prefixIndex = lexeme.indexOf("#");
        final String digits = lexeme.substring(prefixIndex + 1);

        BigInteger initialValue = (digits.length() <= this.getMaxDigits())
                ? new BigInteger(digits, this.getBase())
                : null;

        Subtype subtype = (initialValue != null)
                ? getRange(initialValue)
                : Subtype.UNKNOWN;

        if (subtype.equals(Subtype.UNKNOWN)) {
            initialValue = null;
        }
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        lexeme = MAX_ULINT.toString();
        Subtype subtype = Subtype.LWORD;
        return new ParsedValue(lexeme, subtype, MAX_ULINT);
    }

    /**
     * Determines the smallest unsigned integer subtype that can hold the lexeme.
     *
     * @param value the parsed BigInteger lexeme
     * @return the appropriate subtype
     */
    private Subtype getRange(BigInteger value) {
        if (value.compareTo(MAX_USINT) <= 0) return Subtype.BYTE;
        if (value.compareTo(MAX_UINT)  <= 0) return Subtype.WORD;
        if (value.compareTo(MAX_UDINT) <= 0) return Subtype.DWORD;
        if (value.compareTo(MAX_ULINT) <= 0) return Subtype.LWORD;
        return Subtype.UNKNOWN;
    }

    /**
     * Returns the maximum number of digits for this base.
     *
     * @return maximum digit count
     */
    protected abstract int getMaxDigits();

    /**
     * Returns the numeric base (2, 8, or 16).
     *
     * @return the base
     */
    protected abstract int getBase();

}
