package compiler.parser.facades;

import java.util.List;

/**
 * Parser that distributes the inferior and superior limits of ranges.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class RangesParser {

    private RangesParser() {
        // Utility class - not instantiable
    }

    /**
     * Distributes the inferior and superior limits of the given ranges.
     *
     * @param ranges           the ranges to distribute
     * @param inferiorLimits   list of inferior limits collected so far, extended in place
     * @param superiorLimits   list of superior limits collected so far, extended in place
     */
    public static void parse(final List<String> ranges, final List<String> inferiorLimits,
            final List<String> superiorLimits) {
        boolean toInferior = true;
        for (String rangeLimit : ranges) {
            if (toInferior) {
                inferiorLimits.add(rangeLimit);
            } else {
                superiorLimits.add(rangeLimit);
            }
            toInferior = !toInferior;
        }
    }
}
