package utils.enums;

/**
 * Specific data subtypes for IEC 61131-7 variables and literals.
 * <p>
 * Covers all elementary types defined in the standard including
 * signed/unsigned integers, floating-point, time/date, bit strings,
 * and boolean types.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */

public enum Subtype {
    BOOL,
    BYTE,
    /** Custom/user-defined type. */
    CUSTOM,
    DATE,
    DATE_AND_TIME,
    DINT,
    DWORD,
    INT,
    LINT,
    LREAL,
    LWORD,
    REAL,
    SINT,
    STRING,
    TIME,
    TIME_OF_DAY,
    UDINT,
    UINT,
    ULINT,
    UNKNOWN,
    USINT,
    WORD,
    WSTRING
}