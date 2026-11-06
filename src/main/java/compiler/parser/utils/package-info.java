/**
 * Parser utility classes.
 * <p>
 * Contains supporting classes for semantic actions and scope management:
 * </p>
 * <ul>
 *   <li>{@link compiler.parser.metadata.ArrayMetadata} - holds array type info: element subtype, custom type, dimension ranges</li>
 *   <li>{@link compiler.parser.utils.ArrayParsingContext} - context for parsing repeated array initializations</li>
 *   <li>{@link compiler.parser.utils.PathInitializationInfo} - pairs a field name with its Initialization node</li>
 * </ul>
 *
 * @see compiler.parser.metadata.ArrayMetadata
 * @see compiler.parser.utils.ArrayParsingContext
 * @see compiler.parser.utils.PathInitializationInfo
 * @since 1.0
 */
package compiler.parser.utils;
