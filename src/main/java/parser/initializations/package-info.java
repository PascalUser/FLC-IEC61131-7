/**
 * Polymorphic initialization hierarchy for IEC 61131-7.
 * <p>
 * Models all forms of variable initialization as {@link parser.initializations.Initialization} subtypes:
 * </p>
 * <ul>
 *   <li>{@link parser.initializations.VariableInitialization} - explicit user assignments</li>
 *   <li>{@link parser.initializations.primitives.BooleanInitialization} - default BOOL (FALSE)</li>
 *   <li>{@link parser.initializations.primitives.RealInitialization} - default REAL (0.0)</li>
 *   <li>{@link parser.initializations.EnumeratedInitialization} - default enum (first value)</li>
 *   <li>{@link parser.initializations.MacroInitialization} - explicit enum literal reference</li>
 *   <li>{@link parser.initializations.SubrangeInitialization} - default subrange (lower bound)</li>
 *   <li>{@link parser.initializations.RepeatedInitialization} - arrays with repetition factors and intervals</li>
 *   <li>{@link parser.initializations.StructInitialization} - structs with named field initialization</li>
 * </ul>
 *
 * @see parser.initializations.Initialization
 * @since 1.0
 */
package parser.initializations;