package lexer.transformers;

public abstract class Transformer {
    private Transformer next = null;

    Transformer(Transformer next) {
        this.next = next;
    }

    abstract String transform(String lexeme);

    protected String giveToNext(String lexeme) {
        if (next == null) {
            return lexeme;
        }
        return next.transform(lexeme);
    }
}
