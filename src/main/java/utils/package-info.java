/**
 * Core utilities - centralized repository pattern implementation.
 * <p>
 * The {@code utils} package implements the <b>Repository pattern</b> as the single
 * source of truth for all compilation phases. Key components:
 * </p>
 * <ul>
 *   <li>{@link utils.SymbolTable} - HashMap-based storage for {@link utils.LexemeInfo} entries</li>
 *   <li>{@link utils.LexemeInfo} - immutable DTO with complete semantic attributes</li>
 *   <li>{@link utils.DiagnosticsHandler} - centralized error/warning collection</li>
 *   <li>{@link utils.builders.LexemeInfoBuilder} - fluent builder for LexemeInfo</li>
 *   <li>{@link utils.enums.Type}, {@link utils.enums.Subtype}, {@link utils.enums.Use}, {@link utils.enums.Source} -
 *   classification enums</li>
 * </ul>
 * <p>
 * All phases (lexer, parser, semantic) share a single SymbolTable instance,
 * ensuring data consistency and decoupling.
 * </p>
 *
 * @see utils.SymbolTable
 * @see utils.LexemeInfo
 * @see utils.DiagnosticsHandler
 * @since 1.0
 */
package utils;