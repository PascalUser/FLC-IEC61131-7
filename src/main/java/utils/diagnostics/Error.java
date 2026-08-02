package utils.diagnostics;

public abstract class Error extends Diagnostic {

    public Error(int line) {
        super(line);
    }

    @Override
    public String getMessage() {
         return "ERROR - " + super.getMessage() + ", ";
    }

}