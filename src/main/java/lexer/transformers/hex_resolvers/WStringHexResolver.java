package lexer.transformers.hex_resolvers;

import lexer.transformers.Transformer;

public final class WStringHexResolver extends HexResolver {

    public WStringHexResolver(Transformer next) {
        super(next);
    }

    @Override
    protected int getHexDigits() {
        return 4;
    }
}