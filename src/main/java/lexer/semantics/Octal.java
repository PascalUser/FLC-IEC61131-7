package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.OctalOutOfRange;
import java.math.BigInteger;

public class Octal extends BasedAnalyzer {
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615", 8);

    int getMaxDigits() {
        return 32;
    }

    int getBase() {
        return 8;
    }

    BigInteger getMaxConstant() {
        return MAX_ULINT;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new OctalOutOfRange(line, lexeme);
    }
}