package simulation;

import java.util.Arrays;
import java.util.Locale;

/**
 * How far the generated controller is from the ideal one for one output, over the evaluated inputs. Errors are
 * in units of the output (the scale {@code 0 .. 2^N - 1} of the singletons).
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class OutputComparison {
    private final String output;
    private final long evaluated;
    private final double maxError;
    private final int[] worstInputs;
    private final double meanError;
    private final long undefinedInHardware;
    private final long undefinedInReference;

    OutputComparison(final String output, final long evaluated, final double maxError, final int[] worstInputs,
                     final double meanError, final long undefinedInHardware, final long undefinedInReference) {
        this.output = output;
        this.evaluated = evaluated;
        this.maxError = maxError;
        this.worstInputs = worstInputs.clone();
        this.meanError = meanError;
        this.undefinedInHardware = undefinedInHardware;
        this.undefinedInReference = undefinedInReference;
    }

    /** @return name of the output */
    public String output() {
        return output;
    }

    /** @return number of input combinations evaluated */
    public long evaluated() {
        return evaluated;
    }

    /** @return largest absolute error where both controllers are defined */
    public double maxError() {
        return maxError;
    }

    /** @return the inputs of the largest error, in signature order */
    public int[] worstInputs() {
        return worstInputs.clone();
    }

    /** @return mean absolute error where both controllers are defined */
    public double meanError() {
        return meanError;
    }

    /** @return combinations where the generated code divides by zero (all its degrees are 0) */
    public long undefinedInHardware() {
        return undefinedInHardware;
    }

    /** @return combinations where no rule fires in the ideal controller */
    public long undefinedInReference() {
        return undefinedInReference;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s: %d inputs, max error %.2f at %s, mean error %.2f, "
                        + "undefined in hardware %d, undefined in reference %d", output, evaluated, maxError,
                Arrays.toString(worstInputs), meanError, undefinedInHardware, undefinedInReference);
    }
}
