package utils.diagnostics;

public class SyntaxError extends Error {
    public SyntaxError(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " unexpected syntax error";
    }
}
