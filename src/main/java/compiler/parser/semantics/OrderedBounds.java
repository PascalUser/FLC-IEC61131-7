package compiler.parser.semantics;

import compiler.repository.DiagnosticsHandler;
import compiler.repository.diagnostics.general.SemanticError;

import java.math.BigDecimal;
import java.util.List;

public final class OrderedBounds implements SemanticAnalyzer<List<String>> {
    private final DiagnosticsHandler diagnostics;

    public OrderedBounds(DiagnosticsHandler diagnostics) {
        this.diagnostics = diagnostics;
    }

    @Override
    public boolean check(List<String> ranges) {
        boolean ordered = true;
        for (int i = 0; i + 1 < ranges.size(); i += 2) {
            try {
                if (new BigDecimal(ranges.get(i)).compareTo(new BigDecimal(ranges.get(i + 1))) > 0) {
                    diagnostics.add(new SemanticError("lower bound " + ranges.get(i)
                            + " is greater than upper bound " + ranges.get(i + 1)));
                    ordered = false;
                }
            } catch (NumberFormatException e) {
                // not a plain literal (e.g. a prefixed constant): nothing to compare
            }
        }
        return ordered;
    }
}
