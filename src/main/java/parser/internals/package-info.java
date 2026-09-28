/**
 * Parser internal context management.
 * <p>
 * Provides classes for managing parsing scope and name mangling:
 * </p>
 * <ul>
 *   <li>{@link ParsingContext} - holds state for a declaration scope
 *       (identifiers, metadata builder, name manglers)</li>
 *   <li>{@link NameMangler} - generates qualified names for nested symbols
 *       (e.g., {@code TYPE#FIELD#VALUE})</li>
 *   <li>{@link ContextHandler} - stack of parsing contexts for nested scopes</li>
 * </ul>
 *
 * @see ParsingContext
 * @see NameMangler
 * @see ContextHandler
 * @since 1.0
 */
package parser.internals;