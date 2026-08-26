package lexer.semantics;

import utils.diagnostics.Diagnostic;
import utils.diagnostics.OctalOutOfRange;
import utils.enums.Subtype;

import java.math.BigInteger;

/**
 * Semantic analyzer for octal numeric literals (base 8).
 * <p>
 * Handles octal literals with up to 32 digits, mapping them to BYTE, WORD,
 * DWORD, or LWORD subtypes based on digit length.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Octal extends BasedAnalyzer {
    @Override
    int getMaxDigits() {
        return 22;
    }

    @Override
    int getBase() {
        return 8;
    }

    @Override
    protected Diagnostic createDiagnostic(int line, String lexeme) {
        return new OctalOutOfRange(line, lexeme);
    }
}