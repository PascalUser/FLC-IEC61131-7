/**
 * Diagnostic model for compiler errors and warnings.
 * <p>
 * This package defines the common {@link compiler.repository.diagnostics.Diagnostic}
 * abstraction and organizes concrete diagnostics into two main categories:
 * general diagnostics and pinpointed diagnostics.
 * </p>
 *
 * <ul>
 *   <li>
 *     {@link compiler.repository.diagnostics.general.GeneralError} and
 *     {@link compiler.repository.diagnostics.general.GeneralWarning}:
 *     diagnostics that are not necessarily associated with a specific source position.
 *   </li>
 *   <li>
 *     {@link compiler.repository.diagnostics.pinpointed.PinpointedError} and
 *     {@link compiler.repository.diagnostics.pinpointed.PinpointedWarning}:
 *     diagnostics associated with a source location.
 *   </li>
 * </ul>
 *
 * @see compiler.repository.diagnostics.Diagnostic
 * @since 1.0
 */
package compiler.repository.diagnostics;