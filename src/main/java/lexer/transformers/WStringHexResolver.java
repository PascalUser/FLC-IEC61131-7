package lexer.transformers;

public class WStringHexResolver extends HexResolver {

    public WStringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 4;
    }
}