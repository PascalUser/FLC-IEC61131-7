package lexer.semantics.numbers.bases;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.OctalOutOfRange;

/**
 * Semantic analyzer for octal numeric literals (base 8).
 * <p>
 * Handles octal literals with up to 22 digits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on digit length.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
public final class Octal extends BaseNumbersAnalyzer {
    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new OctalOutOfRange(line, lexeme);
    }

    @Override
    protected int getMaxDigits() {
        return 22;
    }

    @Override
    protected int getBase() {
        return 8;
    }
}