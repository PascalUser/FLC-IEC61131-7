/**
 * Shared semantic repository used across compiler phases.
 * <p>
 * This package centralizes semantic information produced and consumed
 * during compilation. It is a shared component of the compiler architecture,
 * rather than a collection of unrelated utilities.
 * </p>
 * <p>
 * Main responsibilities include:
 * </p>
 * <ul>
 *   <li>{@link compiler.repository.SymbolTable}: stores and exposes
 *       semantic information about declared lexemes.</li>
 *   <li>{@link compiler.repository.LexemeInfo}: represents the semantic
 *       attributes associated with a lexeme.</li>
 *   <li>{@link compiler.repository.DiagnosticsHandler}: collects
 *       compilation diagnostics.</li>
 *   <li>{@link compiler.repository.builders.LexemeInfoBuilder}:
 *       supports the construction of lexeme information.</li>
 * </ul>
 * <p>
 * The lexer and parser share repository state so that declarations,
 * semantic classifications, and diagnostics remain available across
 * compilation phases.
 * </p>
 *
 * @see compiler.repository.SymbolTable
 * @see compiler.repository.LexemeInfo
 * @see compiler.repository.DiagnosticsHandler
 * @since 1.0
 */
package compiler.repository;