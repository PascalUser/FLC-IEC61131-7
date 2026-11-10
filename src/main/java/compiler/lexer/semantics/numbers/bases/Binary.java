package compiler.lexer.semantics.numbers.bases;

import compiler.repository.diagnostics.Diagnostic;
import compiler.repository.diagnostics.pinpointed.outofrange.base.BinaryOutOfRange;

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

    /** {@inheritDoc} */
    @Override
    protected Diagnostic createDiagnostic(final int line, final String lexeme) {
        return new BinaryOutOfRange(line, lexeme, CORRECTED);
    }

    /** {@inheritDoc} */
    @Override
    protected int getMaxDigits() {
        return MAX_DIGITS;
    }

    /** {@inheritDoc} */
    @Override
    protected int getBase() {
        return 2;
    }
}
