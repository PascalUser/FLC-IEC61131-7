package compiler.lexer.semantics.numbers.bases;

import compiler.repository.diagnostics.Diagnostic;
import compiler.repository.diagnostics.pinpointed.outofrange.base.HexadecimalOutOfRange;

/**
 * Semantic analyzer for hexadecimal numeric literals (base 16).
 * <p>
 * Handles hexadecimal literals with up to 16 digits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on digit length.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class Hexadecimal extends BaseNumbersAnalyzer {
    /** {@inheritDoc} */
    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new HexadecimalOutOfRange(line, lexeme, CORRECTED);
    }

    /** {@inheritDoc} */
    @Override
    protected int getMaxDigits() {
        return 16;
    }

    /** {@inheritDoc} */
    @Override
    protected int getBase() {
        return 16;
    }
}
