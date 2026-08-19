package lexer.transformers;

import org.jspecify.annotations.NonNull;

public class StripTypePrefix extends Transformer {
    StripTypePrefix(Transformer next) {
        super(next);
    }

    String transform(@NonNull String lexeme) {
        final int index = lexeme.indexOf('#');
        return super.giveToNext(lexeme.substring(index + 1));
    }
}
