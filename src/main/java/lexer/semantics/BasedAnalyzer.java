package lexer.semantics;
import org.jspecify.annotations.NonNull;
import utils.enums.Subtype;
import java.math.BigInteger;

/**
 * Abstract analyzer for based numeric literals (binary, octal, hexadecimal).
 * <p>
 * Extends {@link NumericAnalyzer} to handle literals with explicit base prefixes.
 * Determines the appropriate subtype based on the number of digits and
 * provides error correction by clamping to the maximum representable lexeme.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class BasedAnalyzer extends NumericAnalyzer {
    protected static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615", 16);

    @Override
    protected ParsedValue parse(String lexeme) {
        BigInteger initialValue = (lexeme.length() <= this.getMaxDigits())
                ? new BigInteger(lexeme, this.getBase())
                : null;
        Subtype subtype = (initialValue != null)
                ? getRange(lexeme.length())
                : Subtype.UNKNOWN;
        return new ParsedValue(lexeme, subtype, initialValue);
    }

    /**
     * Returns the maximum number of digits for this base.
     *
     * @return maximum digit count
     */
    abstract int getMaxDigits();

    /**
     * Returns the numeric base (2, 8, or 16).
     *
     * @return the base
     */
    abstract int getBase();

    /**
     * Determines the subtype based on digit length.
     *
     * @param length number of digits in the lexeme
     * @return the smallest subtype that can hold the lexeme
     */
    private Subtype getRange(int length) {
        int shift = getShiftAmount(getBase());
        if (length <=  8 >> shift) return Subtype.BYTE;
        if (length <= 32 >> shift) return Subtype.WORD;
        if (length <= 64 >> shift) return Subtype.DWORD;
        return Subtype.LWORD;
    }

    /**
     * Calculates bit shift amount for the given base.
     *
     * @param base the numeric base
     * @return shift amount (0 for binary, 1 for octal, 2 for hex)
     * @throws IllegalArgumentException if base is not 2, 8, or 16
     */
    private int getShiftAmount(int base) {
        switch (base) {
            case 2:
                return 0;
            case 8:
                return 1;
            case 16:
                return 2;
            default:
                throw new IllegalArgumentException("Unsupported base: " + base);
        }
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        lexeme = this.getMaxConstant().toString();
        Subtype subtype = Subtype.LWORD;
        return new ParsedValue(lexeme, subtype, this.getMaxConstant());
    }

    /**
     * Returns the maximum constant lexeme for this base.
     *
     * @return maximum lexeme as BigInteger
     */
    abstract BigInteger getMaxConstant();
}
