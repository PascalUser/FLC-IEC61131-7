/**
 * Classification enumerations for semantic attributes.
 * <p>
 * Four enums categorize every symbol in the symbol table:
 * </p>
 * <ul>
 *   <li>{@link Type} - general classification: SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT</li>
 *   <li>{@link Subtype} - specific primitive type or CUSTOM/NONE</li>
 *   <li>{@link Use} - usage context: VARIABLE, FIELD, TYPE, MACRO, LITERAL, FUNCTION, RULE, OPTION</li>
 *   <li>{@link Source} - declaration block: IN, OUT, INTERNAL, FUZZIFY, DEFUZZIFY, NONE</li>
 * </ul>
 * <p>
 * The Type+Subtype combination models the full IEC 61131-7 type hierarchy.
 * </p>
 *
 * @see Type
 * @see Subtype
 * @see Use
 * @see Source
 * @since 1.0
 */
package utils.enums;