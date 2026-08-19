package lexer.transformers;

import org.jspecify.annotations.NonNull;

public class StripLeadingZeros extends Transformer {
    StripLeadingZeros(Transformer next) {
        super(next);
    }

    String transform(@NonNull String lexeme) {
        int i = 0;
        while (i < lexeme.length() - 1 && lexeme.charAt(i) == '0')
            i++;

        return super.giveToNext(lexeme.substring(i));
    }
}
