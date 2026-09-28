/**
 * Polymorphic initialization hierarchy for IEC 61131-7.
 * <p>
 * Models all forms of variable initialization as {@link Initialization} subtypes:
 * </p>
 * <ul>
 *   <li>{@link Constant} - simple literal values</li>
 *   <li>{@link VariableInitialization} - explicit user assignments</li>
 *   <li>{@link BooleanInitialization} - default BOOL (FALSE)</li>
 *   <li>{@link RealInitialization} - default REAL (0.0)</li>
 *   <li>{@link EnumeratedInitialization} - default enum (first value)</li>
 *   <li>{@link MacroInitialization} - explicit enum literal reference</li>
 *   <li>{@link SubrangeInitialization} - default subrange (lower bound)</li>
 *   <li>{@link RepeatedInitialization} - arrays with repetition factors and intervals</li>
 *   <li>{@link StructInitialization} - structs with named field initialization</li>
 * </ul>
 *
 * @see Initialization
 * @since 1.0
 */
package parser.initializations;