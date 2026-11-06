package compiler.parser.publishers.nodes;

import compiler.parser.publishers.leafs.single.SingleDeclaration;
import compiler.parser.utils.PathInitializationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Block that groups the declarations of a structure.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class StructureBlock extends Block<SingleDeclaration> {

    /** {@inheritDoc} */
    @Override
    public void add(SingleDeclaration declaration) {
        declarations.add(declaration);
    }

    /**
     * Returns the name and initial value of every field of the structure.
     *
     * @return the field initializations in source order
     */
    public List<PathInitializationInfo> getInitializationInfo() {
        List<PathInitializationInfo> result = new ArrayList<>();
        for (SingleDeclaration fieldDeclaration : declarations) {
            result.add(new PathInitializationInfo(fieldDeclaration.identifier(), fieldDeclaration.initialValue()));
        }
        return result;
    }
}
