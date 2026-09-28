/**
 * Hierarchical diagnostic system (errors and warnings).
 * <p>
 * Provides typed diagnostic classes for precise error categorization:
 * </p>
 * <ul>
 *   <li>{@link Diagnostic} - abstract base with line number</li>
 *   <li>{@link Error} - fatal errors (subclasses: IntegerOutOfRange, RealOutOfRange, ...)</li>
 *   <li>{@link Warning} - non-fatal warnings (subclass: StringLengthWarning)</li>
 *   <li>{@link SyntaxError} - lexer syntax errors</li>
 * </ul>
 * <p>
 * All diagnostics capture source line number and offending lexeme.
 * Collected centrally by {@link utils.DiagnosticsHandler}.
 * </p>
 *
 * @see Diagnostic
 * @see Error
 * @see Warning
 * @see DiagnosticsHandler
 * @since 1.0
 */
package utils.diagnostics;