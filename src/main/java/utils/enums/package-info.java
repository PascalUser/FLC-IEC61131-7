/**
 * Classification enumerations for semantic attributes.
 * <p>
 * Four enums categorize every symbol in the symbol table:
 * </p>
 * <ul>
 *   <li>{@link utils.enums.Type} - general classification: SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT</li>
 *   <li>{@link utils.enums.Subtype} - specific primitive type or CUSTOM/NONE</li>
 *   <li>{@link utils.enums.Use} - usage context: VARIABLE, FIELD, TYPE, MACRO, LITERAL, FUNCTION, RULE, OPTION</li>
 *   <li>{@link utils.enums.Source} - declaration block: IN, OUT, INTERNAL, FUZZIFY, DEFUZZIFY, NONE</li>
 * </ul>
 * <p>
 * The Type+Subtype combination models the full IEC 61131-7 type hierarchy.
 * </p>
 *
 * @see utils.enums.Type
 * @see utils.enums.Subtype
 * @see utils.enums.Use
 * @see utils.enums.Source
 * @since 1.0
 */
package utils.enums;