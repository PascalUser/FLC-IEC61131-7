package lexer.semantics;
import org.jspecify.annotations.NonNull;
import utils.enums.Subtype;
import java.math.BigInteger;

public abstract class BasedAnalyzer extends NumericAnalyzer {
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

    abstract int getMaxDigits();
    abstract int getBase();

    private Subtype getRange(int length) {
        int shift = getShiftAmount(getBase());
        if (length <=  8 >> shift) return Subtype.BYTE;
        if (length <= 32 >> shift) return Subtype.WORD;
        if (length <= 64 >> shift) return Subtype.DWORD;
        return Subtype.LWORD;
    }

    private int getShiftAmount(int base) {
        return switch (base) {
            case 2  -> 0;
            case 8  -> 1;
            case 16 -> 2;
            default -> throw new IllegalArgumentException("Unsupported base: " + base);
        };
    }

    @Override
    protected ParsedValue fallback(@NonNull String lexeme) {
        lexeme = this.getMaxConstant().toString();
        Subtype subtype = Subtype.LWORD;
        return new ParsedValue(lexeme, subtype, this.getMaxConstant());
    }

    abstract BigInteger getMaxConstant();
}
