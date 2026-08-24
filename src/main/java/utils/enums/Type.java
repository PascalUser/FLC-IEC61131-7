package utils.enums;

/**
 * General type classification for symbols in the IEC 61131-7 compiler.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public enum Type {
    UNKNOWN,
    /** Simple scalar type (INT, REAL, BOOL, etc.). */
    SIMPLE,
    ENUMERATE,
    SUBRANGE,
    ARRAY,
    STRUCT
}
