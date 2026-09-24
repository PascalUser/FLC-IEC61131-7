package lexer.semantics.numbers.bases;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.BinaryOutOfRange;

/**
 * Semantic analyzer for binary numeric literals (base 2).
 * <p>
 * Handles binary literals with up to 64 bits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on bit length.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class Binary extends BaseNumbersAnalyzer {
    private static final int MAX_DIGITS = 64;

    @Override
    protected Diagnostic createDiagnostic(final int line, final String lexeme) {
        return new BinaryOutOfRange(line, lexeme);
    }

    @Override
    protected int getMaxDigits() {
        return MAX_DIGITS;
    }

    @Override
    protected int getBase() {
        return 2;
    }
}
