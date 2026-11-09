package compiler.generator.lowers.sublowers;

import compiler.generator.ControllerModel;
import compiler.generator.lowers.Lower;
import compiler.generator.lowers.contexts.LoweringContext;
import compiler.generator.lowers.facades.IntegerDomainResolver;
import compiler.generator.lowers.utils.IntegerDomain;
import compiler.parser.initializations.Initialization;
import compiler.repository.LexemeInfo;
import compiler.repository.diagnostics.InvalidUse;
import compiler.repository.diagnostics.UnsupportedFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BitWidthLower implements Lower<Integer> {
    private static final int UNRESOLVED = -1;
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
        List<String> ioVarKeys = new ArrayList<>(ctx.inVarKeys());
        ioVarKeys.addAll(ctx.outVarKeys());

        int bitsNeeded = NOTHING_TO_INFER;
        String widest = null;

        for (String key : ioVarKeys) {
            Optional<IntegerDomain> domain = IntegerDomainResolver.resolve(ctx.symbolTable.get(key), ctx.symbolTable);
            if (domain.isPresent() && domain.get().bits() > bitsNeeded) {
                bitsNeeded = domain.get().bits();
                widest = ctx.keys.shortName(key);
            }
        }
        if (bitsNeeded > ControllerModel.MAX_BIT_WIDTH) {
            ctx.diagnosticsHandler.add(new UnsupportedFeature(ERR_TOO_WIDE(widest, bitsNeeded)));
            return UNRESOLVED;
        }
        return bitsNeeded;
    }

    public boolean analyzeIfRepresentable(BigDecimal relativeValue) {
        return bitWidth > 0
                && relativeValue.signum() >= 0
                && relativeValue.compareTo(BigDecimal.valueOf(1L << bitWidth)) < 0;
    }

    public int getBitWidth() { return bitWidth; }

    @Override
    public Optional<Integer> adapt() {
        return (bitWidth > 0) ? Optional.of(bitWidth) : Optional.empty();
    }

    private static String ERR_UNSUPPORTED_WIDTH() {
        final int min =  ControllerModel.MIN_BIT_WIDTH;
        final int max =  ControllerModel.MAX_BIT_WIDTH;
        return "PRAGMA bit_width must be an integer between " + min + " and " + max;
    }

    private static String ERR_INVALID_WIDTH() {
        return "PRAGMA bit_width should declare a number of bits";
    }

    private static String ERR_TOO_WIDE(String variable, int bits) {
        final int max =  ControllerModel.MAX_BIT_WIDTH;
        return "the bit width is taken from the widest variable (" + variable + " needs " + bits + " bits), " +
                "but at most " + max + " bits are supported: " +
                "declare PRAGMA bit_width or use a narrower type or subrange";
    }
}
