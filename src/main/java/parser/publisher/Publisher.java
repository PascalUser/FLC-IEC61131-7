package parser.publisher;

import utils.builders.LexemeInfoSchema;

/**
 * Interface for objects that can publish symbol declarations to the symbol table.
 * <p>
 * Extends {@link LexemeInfoSchema} to allow fluent configuration of symbol
 * attributes before publishing. Implementations include {@link Declaration}
 * for single declarations and {@link Compound} for grouped declarations.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface Publisher extends LexemeInfoSchema {
    /**
     * Publishes the configured symbol(s) to the symbol table.
     */
    void publish();
}
