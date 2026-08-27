package utils.diagnostics;

/**
 * Warning for real literals exceeding the floating-point range.
 * <p>
 * The lexer uses a fallback value (Double.MAX_VALUE/-Double.MAX_VALUE) and continues compilation.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class RealOutOfRange extends Warning {
    private final String realLexeme;

    public RealOutOfRange(int line, String outOfBoundReal) {
        super(line);
        this.realLexeme = outOfBoundReal;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + realLexeme + " real out of range";
    }
}