package utils.diagnostics;

/**
 * Warning for string length exceeding maximum allowed length.
 */
public class StringLengthWarning extends Warning {
    
    private final String message;
    
    public StringLengthWarning(int line, String message) {
        super(line);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return "WARNING: Line " + line + ", " + message;
    }
}