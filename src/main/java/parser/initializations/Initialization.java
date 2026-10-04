package parser.initializations;

/**
 * Interface for polymorphic initialization values in IEC 61131-7.
 * <p>
 * The initialization hierarchy supports all IEC 61131-7 initialization forms:
 * </p>
 * <ul>
 * <!-- TODO: {@code Constant} class no longer exists; simple literal values are covered by {@link VariableInitialization}. Verify intended documentation. -->
 *   <li>{@link VariableInitialization} — explicit user assignments</li>
 *   <li>{@link BooleanInitialization} — default BOOL (FALSE)</li>
 *   <li>{@link RealInitialization} — default REAL (0.0)</li>
 *   <li>{@link EnumeratedInitialization} — default enum (first value)</li>
 *   <li>{@link MacroInitialization} — explicit enum literal reference</li>
 *   <li>{@link SubrangeInitialization} — default subrange (lower bound)</li>
 *   <li>{@link RepeatedInitialization} — arrays with repetition factors</li>
 *   <li>{@link StructInitialization} — structs with named field initialization</li>
 * </ul>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface Initialization {
    /**
     * Selects a nested variable/field for multi-level initialization.
     * <p>
     * For simple initializations, returns {@code this}. For struct/array
     * initializations, navigates to the nested initialization.
     * </p>
     *
     * @param variable the variable or field name (or array index)
     * @return the initialization for the selected variable
     */
    Initialization selectVariable(String variable);

    /**
     * Returns the string representation of this initialization's value.
     *
     * @return the value as string
     */
    String getVariableValue();

    /**
     * Creates a deep copy of this initialization.
     *
     * @return a new independent copy
     */
    Initialization copy();
}
