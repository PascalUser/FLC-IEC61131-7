/**
 * Classification enumerations for semantic attributes.
 * <p>
 * Four enums categorize every symbol in the symbol table:
 * </p>
 * <ul>
 *   <li>{@link compiler.repository.enums.Type} - general classification: SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT</li>
 *   <li>{@link compiler.repository.enums.Subtype} - specific primitive type or CUSTOM/NONE</li>
 *   <li>{@link compiler.repository.enums.Use} - usage context: VARIABLE, FIELD, TYPE, MACRO, LITERAL, FUNCTION, RULE, OPTION</li>
 *   <li>{@link compiler.repository.enums.Source} - declaration block: IN, OUT, INTERNAL, FUZZIFY, DEFUZZIFY, NONE</li>
 * </ul>
 * <p>
 * The Type+Subtype combination models the full IEC 61131-7 type hierarchy.
 * </p>
 *
 * @see compiler.repository.enums.Type
 * @see compiler.repository.enums.Subtype
 * @see compiler.repository.enums.Use
 * @see compiler.repository.enums.Source
 * @since 1.0
 */
package compiler.repository.enums;