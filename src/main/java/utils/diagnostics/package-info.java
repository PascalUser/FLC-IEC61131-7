/**
 * Hierarchical diagnostic system (errors and warnings).
 * <p>
 * Provides typed diagnostic classes for precise error categorization:
 * </p>
 * <ul>
 *   <li>{@link utils.diagnostics.Diagnostic} - abstract base with line number</li>
 *   <li>{@link utils.diagnostics.Error} - fatal errors (subclasses:
 *       DateOutOfRange, DateAndTimeOutOfRange, IntervalConstructionError,
 *       IntervalOutOfRange, TimeOfDayOutOfRange, SyntaxError)</li>
 *   <li>{@link utils.diagnostics.Warning} - non-fatal warnings (subclasses:
 *       StringLengthWarning, HexadecimalOutOfRange, RealOutOfRange,
 *       NaturalOutOfRange, BinaryOutOfRange, OctalOutOfRange,
 *       IntegerOutOfRange)</li>
 * </ul>
 * <p>
 * All diagnostics capture source line number and offending lexeme.
 * Collected centrally by {@link utils.DiagnosticsHandler}.
 * </p>
 *
 * @see utils.diagnostics.Diagnostic
 * @see utils.diagnostics.Error
 * @see utils.diagnostics.Warning
 * @see utils.DiagnosticsHandler
 * @since 1.0
 */
package utils.diagnostics;