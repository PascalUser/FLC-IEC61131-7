package lowering.builders;

/**
 * Fluent schema for the values needed to build a {@link codegen.ControllerModel}.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public interface ControllerModelSchema {
    /**
     * Sets the bit width of every controller value.
     *
     * @param newBitWidth bit width in {@link codegen.ControllerModel#MIN_BIT_WIDTH}..{@link codegen.ControllerModel#MAX_BIT_WIDTH}
     * @return this schema
     */
    ControllerModelSchema bitWidth(int newBitWidth);
}
