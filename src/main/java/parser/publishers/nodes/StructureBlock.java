package parser.publishers.nodes;

import parser.publishers.leafs.single.SingleDeclaration;
import parser.utils.PathInitializationInfo;

import java.util.ArrayList;
import java.util.List;

public final class StructureBlock extends Block<SingleDeclaration> {

    @Override
    public void add(SingleDeclaration declaration) {
        declarations.add(declaration);
    }

    public List<PathInitializationInfo> getInitializationInfo() {
        List<PathInitializationInfo> result = new ArrayList<>();
        for (SingleDeclaration fieldDeclaration : declarations) {
            result.add(new PathInitializationInfo(fieldDeclaration.identifier(), fieldDeclaration.initialValue()));
        }
        return result;
    }
}
