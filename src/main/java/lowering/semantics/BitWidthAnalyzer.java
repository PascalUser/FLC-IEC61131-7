package lowering.semantics;

import codegen.ControllerModel;
import lowering.SymbolKeys;
import lowering.builders.ControllerModelBuilder;
import parser.initializations.Initialization;
import utils.DiagnosticsHandler;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.diagnostics.GeneratorWarning;
import utils.diagnostics.UnsupportedFeature;

public class BitWidthAnalyzer implements SemanticAnalyzer {
    private final SymbolTable symbolTable;
    private final SymbolKeys keys;
    private final DiagnosticsHandler diagnosticsHandler;

    private final static int DEFAULT_BIT_WIDTH = 8;
    private final static String MISSING_PRAGMA_MSSG =
            "no PRAGMA bit_width in an OPTION block, using " +
            DEFAULT_BIT_WIDTH +
            " bits";
    private final static String INVALID_BIT_WIDTH =
            "PRAGMA bit_width must be an integer between " +
            ControllerModel.MIN_BIT_WIDTH +
            " and " +
            ControllerModel.MAX_BIT_WIDTH;

    public BitWidthAnalyzer(SymbolTable symbolTable, SymbolKeys keys, DiagnosticsHandler diagnosticsHandler) {
        this.symbolTable = symbolTable;
        this.keys = keys;
        this.diagnosticsHandler = diagnosticsHandler;
    }

    @Override
    public boolean construct(ControllerModelBuilder controller) {
        LexemeInfo pragma = symbolTable.get(keys.pragma("bit_width"));
        if (pragma == null) {
            diagnosticsHandler.add(new GeneratorWarning(MISSING_PRAGMA_MSSG));
            controller.bitWidth(DEFAULT_BIT_WIDTH);
            return true;
        }
        String value = ((Initialization) pragma.initialValue).variableValue();
        try {
            int width = Integer.parseInt(value);
            if (width >= ControllerModel.MIN_BIT_WIDTH && width <= ControllerModel.MAX_BIT_WIDTH) {
                controller.bitWidth(width);
                return true;
            }
            diagnosticsHandler.add(new UnsupportedFeature(INVALID_BIT_WIDTH));
            return false;
        } catch (NumberFormatException e) {
            throw new RuntimeException("BitWidthAnalyzer: pragma value was not a valid number");
        } catch (Exception e) {
            throw new RuntimeException("BitWidthAnalyzer: unexpected error");
        }
    }
}
