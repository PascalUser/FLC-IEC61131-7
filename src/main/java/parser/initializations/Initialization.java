package parser.initializations;

public interface Initialization {
    Initialization selectVariable(String variable);
    String getVariableValue();

    Initialization copy();
}
