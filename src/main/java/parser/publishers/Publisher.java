package parser.publishers;

import parser.publishers.leafs.multiple.MultipleDeclaration;
import parser.publishers.nodes.DeclarationBlock;
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

    Publisher scope(String newScope);

    List<String> keys();

    /**
     * Publishes the configured symbol(s) to the symbol table.
     */
    void publish();
}
