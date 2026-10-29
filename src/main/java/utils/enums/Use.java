package utils.enums;

/**
 * Usage context classification for symbols.
 * <p>
 * Indicates how a symbol is used in the program: as a variable,
 * literal, function, rule, type definition, or option.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public enum Use {
    COMPONENT,
    CONSTANT,
    FIELD,
    /** The function block; its {@code parameters} are its inputs and outputs in declaration order. */
    FUNCTION_BLOCK,
    LITERAL,
    MACRO,
    /** Option of an {@code OPTION} block ({@code PRAGMA name [value];}). */
    PRAGMA,
    RULE,
    TERM,
    TYPE,
    UNKNOWN,
    VARIABLE,
}
