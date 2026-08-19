package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.HexadecimalOutOfRange;
import java.math.BigInteger;

public class Hexadecimal extends BasedAnalyzer {
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615", 16);

    int getMaxDigits() {
        return 16;
    }

    int getBase() {
        return 16;
    }

    BigInteger getMaxConstant() {
        return MAX_ULINT;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new HexadecimalOutOfRange(line, lexeme);
    }
}