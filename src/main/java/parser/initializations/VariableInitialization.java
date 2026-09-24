package parser.initializations;

public final class VariableInitialization implements Initialization {
    private final String value;

    public VariableInitialization(final String value) {
        this.value = value;
    }

    @Override
    public VariableInitialization selectVariable(final String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return value;
    }

    @Override
    public VariableInitialization copy() {
        return new VariableInitialization(value);
    }
}
