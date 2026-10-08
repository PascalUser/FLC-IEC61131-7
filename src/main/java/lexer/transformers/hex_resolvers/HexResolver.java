package lexer.transformers.hex_resolvers;

import lexer.transformers.Transformer;
import org.jspecify.annotations.NonNull;

/**
 * Base transformer for resolving hexadecimal string literals.
 * Subclasses implement {@link #getHexDigits()} to indicate how many hex
 * digits the concrete resolver expects; the transform replaces '$XX'
 * escapes with the corresponding code points, preserving '$$' pairs for
 * later processing.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 * @see Transformer
 * @see StringHexResolver
 * @see WStringHexResolver
 */
public abstract class HexResolver extends Transformer {

    /**
     * Creates a hexadecimal resolver followed by another transformer.
     *
     * @param next the transformer that receives the resolved lexeme
     */
    public HexResolver(Transformer next) {
        super(next);
    }

    /**
     * Returns the number of hexadecimal digits in an escape sequence.
     *
     * @return the number of digits following the dollar sign
     */
    protected abstract int getHexDigits();

    @Override
    public String transform(@NonNull String lexeme) {
        StringBuilder result = new StringBuilder();
        int hexDigits = getHexDigits();

        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);

            if (c == '$' && i + 1 < lexeme.length()) {
                // Preserve '$$' intact and skip both characters
                // for StringEscapeResolver to process later
                if (lexeme.charAt(i + 1) == '$') {
                    result.append("$$");
                    i++;
                    continue;
                }

                // Intenta resolver la secuencia hexadecimal ($HH o $HHHH)
                if (i + 1 + hexDigits <= lexeme.length()) {
                    String hexPart = lexeme.substring(i + 1, i + 1 + hexDigits);

                    if (isValidHex(hexPart)) {
                        int codePoint = Integer.parseInt(hexPart, 16);
                        result.appendCodePoint(codePoint);
                        i += hexDigits;
                        continue;
                    }
                }

                result.append('$');
            } else {
                result.append(c);
            }
        }

        return giveToNext(result.toString());
    }

    private boolean isValidHex(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isHex = (c >= '0' && c <= '9') ||
                    (c >= 'A' && c <= 'F') ||
                    (c >= 'a' && c <= 'f');
            if (!isHex) return false;
        }
        return true;
    }
}