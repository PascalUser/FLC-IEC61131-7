package lexer.semantics.strings;

import utils.enums.Subtype;

/**
 * Semantic analyzer for WSTRING literals.
 * <p>
 * Determines the {@link Subtype#WSTRING} subtype for double-byte (wide) string literals.
 * The actual validation and processing is handled by {@link StringsAnalyzer}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see StringsAnalyzer
 * @see Strings
 */
public final class WStrings extends StringsAnalyzer {

    @Override
    protected Subtype getSubtype() {
        return Subtype.WSTRING;
    }
}
