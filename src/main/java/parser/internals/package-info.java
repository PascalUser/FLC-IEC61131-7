/**
 * Parser internal context management.
 * <p>
 * Provides classes for managing parsing scope and name mangling:
 * </p>
 * <ul>
 *   <li>{@link parser.internals.ParsingContext} - holds state for a declaration scope
 *       (identifiers, metadata builder, name manglers)</li>
 *   <li>{@link parser.internals.NameMangler} - generates qualified names for nested symbols
 *       (e.g., {@code TYPE#FIELD#VALUE})</li>
 *   <li>{@link parser.internals.ContextHandler} - stack of parsing contexts for nested scopes</li>
 * </ul>
 *
 * @see parser.internals.ParsingContext
 * @see parser.internals.NameMangler
 * @see parser.internals.ContextHandler
 * @since 1.0
 */
package parser.internals;