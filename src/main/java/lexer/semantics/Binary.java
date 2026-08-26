package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.BinaryOutOfRange;
import java.math.BigInteger;

/**
 * Semantic analyzer for binary numeric literals (base 2).
 * <p>
 * Handles binary literals with up to 64 bits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on bit length.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Binary extends BasedAnalyzer {
    @Override
    int getMaxDigits() {
        return 64;
    }

    @Override
    int getBase() {
        return 2;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new BinaryOutOfRange(line, lexeme);
    }
}
