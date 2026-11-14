package compiler.parser.semantics;

import compiler.parser.utils.ParsedInitializer;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.diagnostics.general.SemanticError;

import java.util.List;

public final class InitializerIsEnumeratedValue implements SemanticAnalyzer<ParsedInitializer> {
    private final List<String> enumeratedValues;
    private final DiagnosticsHandler diagnostics;

    public InitializerIsEnumeratedValue(List<String> enumeratedValues, DiagnosticsHandler diagnostics) {
        this.enumeratedValues = enumeratedValues;
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(ParsedInitializer parsed) {
        if (!enumeratedValues.contains(parsed.name())) {
            String errMssg = ERR_UNKNOWN_MACRO(parsed.name());
            diagnostics.add(new SemanticError(errMssg));
            return false;
        }
        return true;
    }

    private String ERR_UNKNOWN_MACRO(String macro) {
        return "'" + macro + "' is not an enumerated value";
    }
}
