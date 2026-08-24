package lexer.transformers;

public class Nothing extends Transformer {
    public Nothing(Transformer next) {
        super(next);
    }

    public Nothing() {
        this(null);
    }

    @Override
    public String transform(String lexeme) {
        return lexeme;
    }
}
