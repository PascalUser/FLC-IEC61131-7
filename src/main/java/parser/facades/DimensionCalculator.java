package parser.facades;

import parser.internals.ParsingContext;
import utils.LexemeInfo;

/**
 * Calculates the total dimension (number of elements) of a multidimensional array.
 * <p>
 * Reads the lower and upper bounds from the context's metadata and computes
 * the product of all dimension sizes.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class DimensionCalculator {

    private DimensionCalculator() {
        // Utility class - not instantiable
    }

    /**
     * Computes the total array dimension from bounds in the context.
     *
     * @param ctx the parsing context containing array bounds in metadata
     * @return the total number of elements (product of all dimension sizes)
     */
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
