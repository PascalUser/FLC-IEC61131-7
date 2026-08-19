package lexer.transformers;

import org.jspecify.annotations.NonNull;

public class UpperCaseConverter extends Transformer {
    UpperCaseConverter(Transformer next) {
        super(next);
    }

    String transform(@NonNull String lexeme) {
        return super.giveToNext(lexeme.toUpperCase());
    }
}
