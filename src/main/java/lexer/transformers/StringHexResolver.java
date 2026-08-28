package lexer.transformers;

public class StringHexResolver extends HexResolver {

    public StringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 2;
    }
}