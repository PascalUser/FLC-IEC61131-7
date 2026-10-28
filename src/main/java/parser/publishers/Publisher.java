package parser.publishers;

import utils.builders.LexemeInfoMutator;

import java.util.List;

/**
 * Interface for objects that can publish symbol declarations to the symbol table.
 * <p>
 * Extends {@link LexemeInfoMutator} to allow fluent configuration of symbol
 * attributes before publishing. Implementations include {@link MultipleDeclaration}
 * for single declarations and {@link DeclarationBlock} for grouped declarations.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface Publisher extends LexemeInfoMutator {

    void setDeclarationScope(String newScope);

    /**
     * Publishes the configured symbol(s) to the symbol table.
     */
    void publish();

    /**
     * Returns the symbol table keys this publisher writes on {@link #publish()}, in declaration order and with
     * the current declaration scope applied (e.g. {@code [MAIN#X, MAIN#Y]} for {@code x, y : INT}).
     * Auxiliary entries published along the way (struct fields, enum macros, terms) are not included.
     *
     * @return the keys of the declared symbols
     */
    List<String> keys();
}
