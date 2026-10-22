package parser.publishers;

import parser.utils.PathInitializationInfo;

import java.util.ArrayList;
import java.util.List;

public final class StructureBlock extends DeclarationGroup {

    public void addField(SingleDeclaration newDeclaration) {
        declarations.add(newDeclaration);
    }

    public List<PathInitializationInfo> getInitializationInfo() {
        List<PathInitializationInfo> result = new ArrayList<>();
        for (Declaration fieldAbstractDecl : declarations) {
            SingleDeclaration fieldDeclaration = (SingleDeclaration) fieldAbstractDecl;
            result.add(new PathInitializationInfo(fieldDeclaration.identifier(), fieldDeclaration.initialValue()));
        }
        return result;
    }
}
