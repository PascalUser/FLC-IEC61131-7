package lowering.lowers;

import java.util.Optional;

/**
 * Adapter from parser metadata to a value of the code generator.
 *
 * @param <ObjetiveType> type produced by this adapter
 * @author Matías Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public interface Lower<ObjetiveType> {
    /**
     * Builds the value, or returns empty when the input is not supported.
     *
     * @return the lowered value, or empty if the input cannot be lowered
     */
    Optional<ObjetiveType> adapt();
}
