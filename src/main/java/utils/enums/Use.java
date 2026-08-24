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
    FUNCTION,
    LITERAL,
    OPTION,
    RULE,
    TYPE,
    UNKNOWN,
    VARIABLE,
}
