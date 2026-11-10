package compiler.repository.diagnostics.general;

public final class SemanticError extends GeneralError {
    private final String detail;

    public SemanticError(final String detail) {
        super();
        this.detail = detail;
    }
    @Override
    public String getMessage() { return super.getMessage() + "semantic error: " + detail; }
}
