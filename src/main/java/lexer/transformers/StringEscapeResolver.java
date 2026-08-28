package lexer.transformers;

import org.jspecify.annotations.NonNull;

public class StringEscapeResolver extends Transformer {

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
                    case 'N': case 'n':
                    case 'L': case 'l':
                        result.append('\n');
                        i++;
                        break;
                    case 'R': case 'r':
                        result.append('\r');
                        i++;
                        break;
                    case 'P': case 'p':
                        result.append('\f');
                        i++;
                        break;
                    case 'T': case 't':
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