package lexer.semantics;

import utils.enums.Subtype;

public class Strings extends StringsAnalyzer {

    @Override
    protected Subtype getSubtype() {
        return Subtype.STRING;
    }
}