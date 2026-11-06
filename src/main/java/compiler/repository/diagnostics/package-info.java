/**
 * Hierarchical diagnostic system (errors and warnings).
 * <p>
 * Provides typed diagnostic classes for precise error categorization:
 * </p>
 * <ul>
 *   <li>{@link compiler.repository.diagnostics.Diagnostic} - abstract base with line number</li>
 *   <li>{@link compiler.repository.diagnostics.Error} - fatal errors (subclasses:
 *       DateOutOfRange, DateAndTimeOutOfRange, IntervalConstructionError,
 *       IntervalOutOfRange, TimeOfDayOutOfRange, SyntaxError)</li>
 *   <li>{@link compiler.repository.diagnostics.Warning} - non-fatal warnings (subclasses:
 *       StringLengthWarning, HexadecimalOutOfRange, RealOutOfRange,
 *       NaturalOutOfRange, BinaryOutOfRange, OctalOutOfRange,
 *       IntegerOutOfRange)</li>
 * </ul>
 * <p>
 * All diagnostics capture source line number and offending lexeme.
 * Collected centrally by {@link compiler.repository.DiagnosticsHandler}.
 * </p>
 *
 * @see compiler.repository.diagnostics.Diagnostic
 * @see compiler.repository.diagnostics.Error
 * @see compiler.repository.diagnostics.Warning
 * @see compiler.repository.DiagnosticsHandler
 * @since 1.0
 */
package compiler.repository.diagnostics;