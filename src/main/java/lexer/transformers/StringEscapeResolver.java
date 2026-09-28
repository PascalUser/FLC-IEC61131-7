package lexer.transformers;

import org.jspecify.annotations.NonNull;

/**
 * Transformer that resolves IEC 61131-7 string escape sequences.
 * <p>
 * IEC 61131-7 defines the following escape sequences in string literals:
 * <ul>
 *   <li>{@code $$} → {@code $}</li>
 *   <li>{@code $'} → {@code '}</li>
 *   <li>{@code $" } → {@code "}</li>
 *   <li>{@code $N}, {@code $n}, {@code $L}, {@code $l} → newline ({@code \n})</li>
 *   <li>{@code $R}, {@code $r} → carriage return ({@code \r})</li>
 *   <li>{@code $P}, {@code $p} → form feed ({@code \f})</li>
 *   <li>{@code $T}, {@code $t} → tab ({@code \t})</li>
 * </ul>
 * </p>
 * <p>
 * Unrecognized escape sequences (e.g., {@code $X}) preserve the {@code $}
 * and continue processing.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class StringEscapeResolver extends Transformer {

    /**
     * Creates a string escape resolver.
     *
     * @param next the next transformer in the chain
     */
    public StringEscapeResolver(Transformer next) {
        super(next);
    }

    @Override
    public String transform(@NonNull String lexeme) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lexeme.length(); i++) {
            char c = lexeme.charAt(i);

            if (c == '$' && i + 1 < lexeme.length()) {
                char next = lexeme.charAt(i + 1);

                switch (next) {
                    case '$':
                        result.append('$');
                        i++;
                        break;
                    case '\'':
                        result.append('\'');
                        i++;
                        break;
                    case '"':
                        result.append('"');
                        i++;
                        break;
                    case 'N':
                    case 'n':
                    case 'L':
                    case 'l':
                        result.append('\n');
                        i++;
                        break;
                    case 'R':
                    case 'r':
                        result.append('\r');
                        i++;
                        break;
                    case 'P':
                    case 'p':
                        result.append('\f');
                        i++;
                        break;
                    case 'T':
                    case 't':
                        result.append('\t');
                        i++;
                        break;
                    default:
                        result.append("$");
                        break;
                }
            } else {
                result.append(c);
            }
        }
        return giveToNext(result.toString());
    }
}