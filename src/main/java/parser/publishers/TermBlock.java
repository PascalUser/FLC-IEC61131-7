package parser.publishers;

import java.util.ArrayList;
import java.util.List;

public class TermBlock extends DeclarationGroup {

    public void addTerm(SingleDeclaration term) {
        declarations.add(term);
    }

    public List<String> getTermNames() {
        List<String> copy = new ArrayList<>();
        for (Declaration declaration : declarations) {
            SingleDeclaration termDecl = (SingleDeclaration) declaration;
            copy.add(termDecl.identifier());
        }
        return copy;
    }
}
