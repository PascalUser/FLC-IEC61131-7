package parser.facades;

import java.util.List;

/**
 * Calculates the total dimension (number of elements) of a multidimensional array.
 * <p>
 * Computes the product of all dimension sizes from the provided lower and upper bounds.
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
     * Computes the total array dimension from lower and upper bounds.
     *
     * @param inferiorLimits the list of lower bounds for each dimension
     * @param superiorLimits the list of upper bounds for each dimension
     * @return the total number of elements (product of all dimension sizes)
     */
    public static int calculate(List<String> inferiorLimits, List<String> superiorLimits) {
        int dimension = 1;
        for (int i = 0; i < inferiorLimits.size(); i++) {
            int ilimit = Integer.parseInt(inferiorLimits.get(i));
            int slimit = Integer.parseInt(superiorLimits.get(i));

            dimension *= (slimit + 1 - ilimit);
        }
        return dimension;
    }
}
