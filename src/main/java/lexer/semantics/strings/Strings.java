package lexer.semantics.strings;

import utils.enums.Subtype;

/**
 * Semantic analyzer for STRING literals.
 * <p>
 * Determines the {@link Subtype#STRING} subtype for single-byte string literals.
 * The actual validation and processing is handled by {@link StringsAnalyzer}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see StringsAnalyzer
 * @see WStrings
 */
public final class Strings extends StringsAnalyzer {

    @Override
    protected Subtype getSubtype() {
        return Subtype.STRING;
    }
}
