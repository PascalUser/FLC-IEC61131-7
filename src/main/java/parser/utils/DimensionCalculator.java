package parser.utils;

import parser.internals.ParsingContext;
import utils.LexemeInfo;

public final class DimensionCalculator {
    public static int calculate(ParsingContext ctx) {
        LexemeInfo metadata = ctx.metadataBuilder().build();
        int dimension = 1;
        for (int i = 0; i < metadata.inferiorLimits.size(); i++) {
            int ilimit = Integer.parseInt(metadata.inferiorLimits.get(i));
            int slimit = Integer.parseInt(metadata.superiorLimits.get(i));

            dimension *= (slimit + 1 - ilimit);
        }
        return dimension;
    }
}
