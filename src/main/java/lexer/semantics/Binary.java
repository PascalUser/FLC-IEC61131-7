package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.BinaryOutOfRange;
import java.math.BigInteger;

public class Binary extends BasedAnalyzer {
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615", 2);

    int getMaxDigits() {
        return 64;
    }

    int getBase() {
        return 2;
    }

    BigInteger getMaxConstant() {
        return MAX_ULINT;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new BinaryOutOfRange(line, lexeme);
    }
}
