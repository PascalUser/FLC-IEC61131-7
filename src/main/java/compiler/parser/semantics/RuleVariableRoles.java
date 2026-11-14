package compiler.parser.semantics;

import compiler.ast.ruleblock.RuleBlockNode;
import compiler.parser.utils.IdentifierResolver;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.UndeclaredReference;
import compiler.repository.enums.Use;

public final class RuleVariableRoles implements SemanticAnalyzer<RuleBlockNode> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;
    private final IdentifierResolver idResolver;

    public RuleVariableRoles(SymbolTable symbolTable, DiagnosticsHandler diagnostics, IdentifierResolver idResolver) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
        this.idResolver = idResolver;
    }

    @Override
    public boolean check(RuleBlockNode ruleBlock) {
        boolean valid = true;
        for (RuleIs ruleIs : RuleIs.allOf(ruleBlock, idResolver)) {
            String varId = ruleIs.is.variable().label();
            LexemeInfo variable = symbolTable.get(varId);
            if (variable == null || variable.use != Use.VARIABLE || variable.source != ruleIs.role) {
                String errMssg = ERR_INVALID_USE(ruleIs.rule, ruleIs.block, idResolver.name(varId));
                diagnostics.add(new UndeclaredReference(errMssg));
                valid = false;
            }
        }
        return valid;
    }

    private static String ERR_INVALID_USE(String rule, String block, String varId) {
        return "conditions must have inputs in conditions and outputs in conclusions, " +
                "but rule " + rule + " of " + block + " uses " + varId;
    }
}
