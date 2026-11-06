package compiler.generator.lowers;

import compiler.generator.ControllerModel;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.generator.lowers.facades.UnsignedIntegerTypes;
import compiler.parser.initializations.Initialization;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.InvalidUse;
import compiler.repository.diagnostics.UnsupportedFeature;
import compiler.repository.enums.Subtype;

import java.math.BigDecimal;
import java.util.Optional;

public final class BitWidthLower implements Lower<Integer> {
    /** Pragma or inferred width out of range; already reported. */
    private static final int UNRESOLVED = -1;
    /** No supported variable to infer from; the variables are reported by their own lowering. */
    private static final int NOTHING_TO_INFER = 0;

    private final int bitWidth;

    public BitWidthLower(LoweringContext ctx) {
        LexemeInfo pragma = ctx.symbolTable.get(ctx.keys.pragma("bit_width"));
        bitWidth = (pragma != null) ? fromPragma(ctx, pragma) : fromVariables(ctx);
    }

    private static int fromPragma(LoweringContext ctx, LexemeInfo pragma) {
        String value = ((Initialization) pragma.initialValue).variableValue();
        try {
            int width = Integer.parseInt(value);
            if (width >= ControllerModel.MIN_BIT_WIDTH && width <= ControllerModel.MAX_BIT_WIDTH) {
                return width;
            }
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_UNSUPPORTED_WIDTH()));
        } catch (NumberFormatException e) {
            ctx.diagnosticsHandler.add(new InvalidUse(ERR_INVALID_WIDTH()));
        }
        return UNRESOLVED;
    }

    private static int fromVariables(LoweringContext ctx) {
        int bitsNeeded = NOTHING_TO_INFER;
        Subtype widestType = Subtype.UNKNOWN;
        for (String key : ctx.ioVarKeys()) {
            LexemeInfo keyInfo = ctx.symbolTable.get(key);
            if (!UnsignedIntegerTypes.accepts(keyInfo)) {
                continue;
            }
            int bits = UnsignedIntegerTypes.bits(keyInfo.subtype);
            if (bits > bitsNeeded) {
                bitsNeeded = bits;
                widestType = keyInfo.subtype;
            }
        }
        if (bitsNeeded > ControllerModel.MAX_BIT_WIDTH) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_TYPE_TOO_WIDE(widestType, bitsNeeded)));
            return UNRESOLVED;
        }
        return bitsNeeded;
    }

    public boolean analyzeIfRepresentable(BigDecimal integerValue) {
        if (bitWidth > 0) {
            return integerValue.compareTo(BigDecimal.valueOf(1L << bitWidth)) < 0;
        }
        return false;
    }

    /** @return the bit width, or a non-positive value if it could not be resolved */
    public int getBitWidth() {
        return bitWidth;
    }

    @Override
    public Optional<Integer> adapt() {
        if (bitWidth > 0) {
            return Optional.of(bitWidth);
        }
        return Optional.empty();
    }

    private static String ERR_UNSUPPORTED_WIDTH() {
        int min = ControllerModel.MIN_BIT_WIDTH;
        int max = ControllerModel.MAX_BIT_WIDTH;
        return "PRAGMA bit_width must be an integer between " + min + " and " + max;
    }

    private static String ERR_INVALID_WIDTH() {
        return "PRAGMA bit_width should declare a number o bits";
    }

    private static String ERR_TYPE_TOO_WIDE(Subtype type, int bits) {
        return "the bit width is taken from the widest type of the inputs and outputs (" + type + ", " + bits
                + " bits), but at most " + ControllerModel.MAX_BIT_WIDTH
                + " bits are supported: declare PRAGMA bit_width or use a narrower type";
    }
}
