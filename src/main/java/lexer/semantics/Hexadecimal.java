package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.HexadecimalOutOfRange;
import java.math.BigInteger;

/**
 * Semantic analyzer for hexadecimal numeric literals (base 16).
 * <p>
 * Handles hexadecimal literals with up to 16 digits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on digit length.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Hexadecimal extends BasedAnalyzer {
    @Override
    int getMaxDigits() {
        return 16;
    }

    @Override
    int getBase() {
        return 16;
    }

    @Override
    BigInteger getMaxConstant() {
        return MAX_ULINT;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new HexadecimalOutOfRange(line, lexeme);
    }
}