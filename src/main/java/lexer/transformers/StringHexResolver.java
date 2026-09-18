package lexer.transformers;

public final class StringHexResolver extends HexResolver {

    public StringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 2;
    }
}