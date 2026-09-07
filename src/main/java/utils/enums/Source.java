package utils.enums;

/**
 * Source block classification for variable declarations.
 * <p>
 * Indicates which block a variable belongs to in a function block:
 * input variables, output variables, internal variables, or
 * fuzzify/defuzzify blocks.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public enum Source {
    DEFUZZIFY,
    FUZZIFY,
    IN,
    INTERNAL,
    NONE,
    OUT,
    UNKNOWN,
}
