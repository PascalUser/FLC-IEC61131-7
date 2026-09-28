/**
 * Core utilities - centralized repository pattern implementation.
 * <p>
 * The {@code utils} package implements the <b>Repository pattern</b> as the single
 * source of truth for all compilation phases. Key components:
 * </p>
 * <ul>
 *   <li>{@link SymbolTable} - HashMap-based storage for {@link LexemeInfo} entries</li>
 *   <li>{@link LexemeInfo} - immutable DTO with complete semantic attributes</li>
 *   <li>{@link DiagnosticsHandler} - centralized error/warning collection</li>
 *   <li>{@link builders.LexemeInfoBuilder} - fluent builder for LexemeInfo</li>
 *   <li>{@link enums.Type}, {@link Subtype}, {@link Use}, {@link Source} - classification enums</li>
 * </ul>
 * <p>
 * All phases (lexer, parser, semantic) share a single SymbolTable instance,
 * ensuring data consistency and decoupling.
 * </p>
 *
 * @see SymbolTable
 * @see LexemeInfo
 * @see DiagnosticsHandler
 * @since 1.0
 */
package utils;