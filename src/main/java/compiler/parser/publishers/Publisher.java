package compiler.parser.publishers;

import compiler.parser.publishers.leafs.multiple.MultipleDeclaration;
import compiler.parser.publishers.nodes.DeclarationBlock;
import compiler.repository.builders.LexemeInfoMutator;

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

    /**
     * Sets the scope in which the symbols are published.
     *
     * @param newScope name of the scope
     * @return this publisher
     */
    Publisher scope(String newScope);

    /**
     * Returns the symbol table keys of the symbols that this publisher will publish.
     *
     * @return the keys in declaration order
     */
    List<String> keys();

    /**
     * Publishes the configured symbol(s) to the symbol table.
     */
    void publish();
}
