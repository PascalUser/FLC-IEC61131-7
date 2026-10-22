/**
 * Parser utility classes.
 * <p>
 * Contains supporting classes for semantic actions and scope management:
 * </p>
 * <ul>
 *   <li>{@link parser.utils.metadata.ArrayMetadata} - holds array type info: element subtype, custom type, dimension ranges</li>
 *   <li>{@link parser.utils.ParsingRepeatedArrayContext} - context for parsing repeated array initializations</li>
 *   <li>{@link parser.utils.PathInitializationInfo} - pairs a field name with its Initialization node</li>
 * </ul>
 *
 * @see parser.utils.metadata.ArrayMetadata
 * @see parser.utils.ParsingRepeatedArrayContext
 * @see parser.utils.PathInitializationInfo
 * @since 1.0
 */
package parser.utils;
