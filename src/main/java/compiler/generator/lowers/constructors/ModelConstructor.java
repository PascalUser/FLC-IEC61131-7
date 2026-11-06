package compiler.generator.lowers.constructors;

import compiler.generator.lowers.builders.ControllerModelBuilder;

/**
 * Stage that contributes one part of a {@link ControllerModelBuilder}.
 *
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public interface ModelConstructor {

    /**
     * Adds this stage's part to the controller builder.
     *
     * @param controller builder that accumulates the parts of the model
     * @return {@code true} if the part was added, {@code false} if the program is unsupported
     */
    boolean construct(ControllerModelBuilder controller);
}
