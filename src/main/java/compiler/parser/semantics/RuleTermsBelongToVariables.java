package compiler.parser.semantics;

import compiler.ast.ruleblock.RuleBlockNode;
import compiler.parser.facades.ComponentResolver;
import compiler.parser.utils.IdentifierResolver;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.diagnostics.general.UndeclaredReference;

import java.util.Objects;

public final class RuleTermsBelongToVariables implements SemanticAnalyzer<RuleBlockNode> {
    private final SymbolTable symbolTable;
    private final DiagnosticsHandler diagnostics;
    private final IdentifierResolver idResolver;

    public RuleTermsBelongToVariables(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnostics,
            IdentifierResolver idResolver
    ) {
        this.symbolTable = symbolTable;
        this.diagnostics = diagnostics;
        this.idResolver = idResolver;
    }

    @Override
    public boolean check(RuleBlockNode ruleBlock) {
        boolean valid = true;
        for (RuleIs ruleIs : RuleIs.allOf(ruleBlock, idResolver)) {
            String varId = ruleIs.is.variable().label();
            String componentKey = ComponentResolver.resolve(symbolTable, varId);
            if (Objects.equals(componentKey, varId)) {
                // RuleVariableRoles reports that varId cannot have a component
                continue;
            }
            LexemeInfo component = symbolTable.get(componentKey);
            if (component == null || component.parameters == null) {
                continue;
            }
            String termIdWithoutProgramScope = idResolver.name(ruleIs.is.term().label());
            String termName = termIdWithoutProgramScope.substring(1 + termIdWithoutProgramScope.indexOf('#'));
            if (!component.parameters.contains(termName)) {
                String varName = idResolver.name(varId);
                String errMssg = ERR_UNDECLARED_TERM(ruleIs.rule, ruleIs.block, termName, varName);
                diagnostics.add(new UndeclaredReference(errMssg));
                valid = false;
            }
        }
        return valid;
    }

    private static String ERR_UNDECLARED_TERM(String rule, String block, String term, String var) {
        return "in rule " + rule + " of " + block + ", the " + term + " is not from " + var;
    }
}
