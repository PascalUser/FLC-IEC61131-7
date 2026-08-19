package lexer.transformers;

import org.jspecify.annotations.NonNull;

public class UnderscoreRemover extends Transformer {
    UnderscoreRemover(Transformer next) {
        super(next);
    }

    String transform(@NonNull String lexeme) {
        return super.giveToNext(lexeme.replace("_", ""));
    }
}
