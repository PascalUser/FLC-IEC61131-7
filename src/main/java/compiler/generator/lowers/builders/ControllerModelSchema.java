package compiler.generator.lowers.builders;

import compiler.generator.ControllerModel;

/**
 * Fluent schema for the values needed to build a {@link ControllerModel}.
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
     * @param newBitWidth bit width in {@link ControllerModel#MIN_BIT_WIDTH}..{@link ControllerModel#MAX_BIT_WIDTH}
     * @return this schema
     */
    ControllerModelSchema bitWidth(int newBitWidth);
}
