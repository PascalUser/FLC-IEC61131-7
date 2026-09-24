package lexer.semantics.strings;

import utils.enums.Subtype;

public final class WStrings extends StringsAnalyzer {

    @Override
    protected Subtype getSubtype() {
        return Subtype.WSTRING;
    }
}
