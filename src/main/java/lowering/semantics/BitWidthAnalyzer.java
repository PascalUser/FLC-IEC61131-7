package lowering.semantics;

import codegen.ControllerModel;
import lowering.builders.ControllerModelBuilder;
import lowering.contexts.LoweringContext;
import parser.initializations.Initialization;
import utils.LexemeInfo;
import utils.diagnostics.GeneratorWarning;
import utils.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;

public class BitWidthAnalyzer implements SemanticAnalyzer {
    private final static int DEFAULT_BIT_WIDTH = 8;
    private final int bitWidth;

    public BitWidthAnalyzer(LoweringContext ctx) {
        LexemeInfo pragma = ctx.symbolTable.get(ctx.keys.pragma("bit_width"));
        if (pragma == null) {
            bitWidth = DEFAULT_BIT_WIDTH;
            ctx.diagnosticsHandler.add(new GeneratorWarning(ERR_MISSING_PRAGMA()));
            return;
        }
        String value = ((Initialization) pragma.initialValue).variableValue();
        try {
            int width = Integer.parseInt(value);
            if (width >= ControllerModel.MIN_BIT_WIDTH && width <= ControllerModel.MAX_BIT_WIDTH) {
                bitWidth = width;
            } else {
                ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_WIDTH()));
                bitWidth = -1;
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("BitWidthAnalyzer: pragma value was not a valid number");
        } catch (Exception e) {
            throw new RuntimeException("BitWidthAnalyzer: unexpected error");
        }
    }

    public boolean analyzeIfRepresentable(BigDecimal integerValue) {
        if (bitWidth != -1) {
            int maxValue = 1 << bitWidth;
            return integerValue.compareTo(BigDecimal.valueOf(maxValue)) < 0;

        }
        return false;
    }

    public int getBitWidth() {
        return bitWidth;
    }

    @Override
    public boolean construct(ControllerModelBuilder controller) {
        if (bitWidth != -1) {
            controller.bitWidth(bitWidth);
            return true;
        }
        return false;
    }

    private static String ERR_MISSING_PRAGMA() {
        return "no PRAGMA bit_width in an OPTION block, using " +  DEFAULT_BIT_WIDTH + "bits";
    }

    private static String ERR_UNSUPPORTED_WIDTH() {
        int min = ControllerModel.MIN_BIT_WIDTH;
        int max = ControllerModel.MAX_BIT_WIDTH;
        return "PRAGMA bit_width must be an integer between " + min + " and " + max;
    }
}
