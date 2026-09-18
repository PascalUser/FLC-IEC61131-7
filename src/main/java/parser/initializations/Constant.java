package parser.initializations;

public final class Constant implements Initialization {
    private final String value;

    public Constant(final String value) {
        this.value = value;
    }

    @Override
    public Constant set(final String value) {
        return this;
    }

    @Override
    public String get() {
        return value;
    }
}
