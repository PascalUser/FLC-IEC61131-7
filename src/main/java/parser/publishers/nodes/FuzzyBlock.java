package parser.publishers.nodes;

import parser.publishers.leafs.single.SingleDeclaration;

import java.util.ArrayList;
import java.util.List;

public final class FuzzyBlock extends Block<SingleDeclaration> {

    @Override
    public void add(SingleDeclaration declaration) {
        declarations.add(declaration);
    }

    public List<String> getTermNames() {
        List<String> copy = new ArrayList<>();
        for (SingleDeclaration declaration : declarations) {
            copy.add(declaration.identifier());
        }
        return copy;
    }
}
