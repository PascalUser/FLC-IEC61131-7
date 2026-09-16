package parser.initializations;

public class Constant implements Initialization {
    String value;

    public Constant(String value) {
        this.value = value;
    }

    @Override
    public Constant set(String value) {
        return this;
    }

    @Override
    public String get() {
        return value;
    }
}
