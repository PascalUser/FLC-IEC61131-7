package lexer.semantics;

import utils.enums.Subtype;

public class WStrings extends StringsAnalyzer {

    @Override
    protected Subtype getSubtype() {
        return Subtype.WSTRING;
    }
}