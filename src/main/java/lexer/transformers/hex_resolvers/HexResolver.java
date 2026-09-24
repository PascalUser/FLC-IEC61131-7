package lexer.transformers.hex_resolvers;

import lexer.transformers.Transformer;
import org.jspecify.annotations.NonNull;

public abstract class HexResolver extends Transformer {

    public HexResolver(Transformer next) {
        super(next);
    }

    protected abstract int getHexDigits();

    @Override
    public String transform(@NonNull String lexeme) {
        StringBuilder result = new StringBuilder();
        int hexDigits = getHexDigits();

        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);

            if (c == '$' && i + 1 < lexeme.length()) {
                // Preserva '$$' intacto y salta ambos caracteres
                // para que StringEscapeResolver los procese después
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