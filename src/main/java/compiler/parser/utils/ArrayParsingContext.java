package compiler.parser.utils;

import java.util.List;

/**
 * Context used to parse the repeated initializations of an array.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class ArrayParsingContext {
    private final int maxValue;
    private final List<PathInitializationInfo> pathsInfo;

    /**
     * Creates a context for parsing the repeated initializations of an array.
     *
     * @param maxValue     maximum value the array can hold
     * @param pathsInfo    initializations of the array fields, by path
     */
    public ArrayParsingContext(final int maxValue, final List<PathInitializationInfo> pathsInfo) {
        this.maxValue = maxValue;
        this.pathsInfo = pathsInfo;
    }

    /**
     * Returns the maximum value the array can hold.
     *
     * @return the maximum value
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Returns the initializations of the array fields, by path.
     *
     * @return the paths and their initializations
     */
    public List<PathInitializationInfo> getPathsInfo() {
        return pathsInfo;
    }
}
