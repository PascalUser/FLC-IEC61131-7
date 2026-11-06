/**
 * Core utilities - centralized repository pattern implementation.
 * <p>
 * The {@code utils} package implements the <b>Repository pattern</b> as the single
 * source of truth for all compilation phases. Key components:
 * </p>
 * <ul>
 *   <li>{@link compiler.repository.SymbolTable} - HashMap-based storage for {@link compiler.repository.LexemeInfo} entries</li>
 *   <li>{@link compiler.repository.LexemeInfo} - immutable DTO with complete semantic attributes</li>
 *   <li>{@link compiler.repository.DiagnosticsHandler} - centralized error/warning collection</li>
 *   <li>{@link compiler.repository.builders.LexemeInfoBuilder} - fluent builder for LexemeInfo</li>
 *   <li>{@link compiler.repository.enums.Type}, {@link compiler.repository.enums.Subtype}, {@link compiler.repository.enums.Use}, {@link compiler.repository.enums.Source} -
 *   classification enums</li>
 * </ul>
 * <p>
 * All phases (lexer, parser, semantic) share a single SymbolTable instance,
 * ensuring data consistency and decoupling.
 * </p>
 *
 * @see compiler.repository.SymbolTable
 * @see compiler.repository.LexemeInfo
 * @see compiler.repository.DiagnosticsHandler
 * @since 1.0
 */
package compiler.repository;