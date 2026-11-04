package lowering.contexts;

import ast.AbstractAstNode;
import ast.AbstractSyntaxTree;
import lowering.SymbolKeys;
import lowering.builders.ControllerModelBuilder;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

public class RuleLoweringContext extends LoweringContext {
    public final ControllerModelBuilder controller;
    public final String ruleblockName;
    public final String ruleNumber;
    public final AbstractAstNode ruleNode;

    public RuleLoweringContext(
            LoweringContext initContext,
            ControllerModelBuilder controller,
            String ruleblockName,
            String ruleNumber,
            AbstractAstNode ruleNode
    ) {
        this(
                initContext.symbolTable,
                initContext.diagnosticsHandler,
                initContext.keys,
                initContext.ast,
                controller,
                ruleblockName,
                ruleNumber,
                ruleNode
        );
    }

    public RuleLoweringContext(
            SymbolTable symbolTable,
            DiagnosticsHandler diagnosticsHandler,
            SymbolKeys keys,
            AbstractSyntaxTree ast,
            ControllerModelBuilder controller,
            String ruleblockName,
            String ruleNumber,
            AbstractAstNode ruleNode
    ) {
        super(symbolTable, diagnosticsHandler, keys, ast);
        this.controller = controller;
        this.ruleblockName = ruleblockName;
        this.ruleNumber = ruleNumber;
        this.ruleNode = ruleNode;
    }
}
