package parser.publishers;

public final class DeclarationBlock extends DeclarationGroup {

    public DeclarationBlock() {
        super();
    }

    public void addDeclaration(Declaration newDeclaration) {
        declarations.add(newDeclaration);
    }
}
