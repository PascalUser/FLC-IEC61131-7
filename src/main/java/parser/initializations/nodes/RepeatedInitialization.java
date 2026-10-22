package parser.initializations.nodes;

import parser.initializations.Initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repeated initialization based on a list of disjoint,
 * contiguous intervals spanning an array dimension.
 * <p>
 * This class manages an array dimension by dividing it into intervals,
 * each associated with an {@link Initialization}. Intervals are kept
 * disjoint and cover the entire dimension range [0, dimension-1].
 * Operations {@link #put(String, Initialization)} and {@link #find(String)}
 * use path strings that may specify a single index (e.g., "2") or a range
 * (e.g., "0..3"), optionally followed by a nested path (e.g., "0..3#field").
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class RepeatedInitialization extends NodeInitialization {
    /** The size of the array dimension this initialization spans. */
    private final int dimension;

    /** Ordered list of disjoint, contiguous intervals covering [0, dimension-1]. */
    private final List<Interval> intervals;

    /**
     * Constructs a new {@code RepeatedInitialization} for the given dimension,
     * initially filled entirely with {@code defaultValue}.
     *
     * @param dimension    the size of the array dimension (must be > 0)
     * @param defaultValue the {@link Initialization} to assign to every index initially
     */
    public RepeatedInitialization(int dimension, Initialization defaultValue) {
        this.dimension = dimension;
        this.intervals = new ArrayList<>();
        this.intervals.add(new Interval(0, dimension - 1, defaultValue));
    }

    /**
     * Inserts or replaces an initialization at the specified path.
     * <p>
     * The path format is {@code "index"} or {@code "start..end"} optionally followed
     * by {@code "#"} and a nested path (e.g., {@code "0..3#field"}).
     * If a nested path is provided, the method delegates to the initialization
     * currently stored in the affected intervals (which must be a {@link NodeInitialization}).
     * The interval list is split as needed to keep intervals disjoint.
     *
     * @param path           the path string identifying the target index or range
     * @param initialization the {@link Initialization} to store
     * @return the previous {@link Initialization} at the first affected index,
     *         or {@code null} if the range was out of bounds
     * @throws RuntimeException if the path targets the root, or if a nested path
     *         is given but the current initialization is {@code null} or not a {@link NodeInitialization}
     */
    @Override
    public Initialization put(final String path, final Initialization initialization) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            throw new RuntimeException("RepeatedInitialization: cannot insert an element in the root");
        }

        String targetStr = brokenPath[0];
        String childPath = brokenPath[1];

        // Parse start and end (supports both "0..3" and "2")
        int start, end;
        if (targetStr.contains("..")) {
            String[] parts = targetStr.split("\\.\\.");
            start = Integer.parseInt(parts[0].trim());
            end = Integer.parseInt(parts[1].trim());
        } else {
            start = Integer.parseInt(targetStr.trim());
            end = start; // A lone index is a range of size 1
        }

        int effectiveStart = Math.max(0, start);
        int effectiveEnd = Math.min(dimension - 1, end);

        if (effectiveStart > effectiveEnd) {
            return null; // Out of range
        }

        List<Interval> newIntervals = new ArrayList<>();
        Initialization oldInitialization = null;

        for (Interval interval : intervals) {
            // Outside the target range
            if (interval.end < effectiveStart || interval.start > effectiveEnd) {
                newIntervals.add(interval);
            } else {
                // Preserve left portion before the overlap
                if (interval.start < effectiveStart) {
                    newIntervals.add(new Interval(interval.start, effectiveStart - 1, interval.initialization));
                }

                // Determine the overlapping region to modify
                int overlapStart = Math.max(interval.start, effectiveStart);
                int overlapEnd = Math.min(interval.end, effectiveEnd);

                // Capture the first old initialization affected for the return value
                if (oldInitialization == null) {
                    oldInitialization = interval.initialization;
                }

                Initialization newInitForOverlap;
                if (childPath.isEmpty()) {
                    // Full replacement (no nested path)
                    newInitForOverlap = initialization;
                } else {
                    // Delegate to nested path (e.g., "0..3#field")
                    if (interval.initialization == null) {
                        throw new RuntimeException(
                                "RepeatedInitialization: element is null, cannot delegate " + childPath
                        );
                    }
                    // IMPORTANT: Clone to avoid mutating shared references if the original interval was larger
                    newInitForOverlap = interval.initialization.copy();
                    NodeInitialization childNode = (NodeInitialization) newInitForOverlap;
                    childNode.put(childPath, initialization);
                }

                newIntervals.add(new Interval(overlapStart, overlapEnd, newInitForOverlap));

                // Preserve right portion after the overlap
                if (interval.end > effectiveEnd) {
                    newIntervals.add(new Interval(effectiveEnd + 1, interval.end, interval.initialization));
                }
            }
        }

        this.intervals.clear();
        this.intervals.addAll(newIntervals);

        return oldInitialization;
    }

    /**
     * Finds the initialization at the specified path.
     * <p>
     * The path format is {@code "index"} or {@code "start..end"} optionally followed
     * by {@code "#"} and a nested path. For a range, the search is based on the first
     * index of the range.
     *
     * @param path the path string identifying the target index or range
     * @return an {@link Optional} containing the {@link Initialization} if found,
     *         or {@link Optional#empty()} if the index is out of bounds or not found
     */
    @Override
    public Optional<Initialization> find(final String path) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            return Optional.of(this);
        }

        String targetStr = brokenPath[0];
        int index;

        try {
            // If a range "0..3" is given, search based on the first element
            if (targetStr.contains("..")) {
                index = Integer.parseInt(targetStr.split("\\.\\.")[0].trim());
            } else {
                index = Integer.parseInt(targetStr.trim());
            }
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (index < 0 || index >= dimension) {
            return Optional.empty();
        }

        // Delegate the search to the interval containing the index
        for (Interval interval : intervals) {
            if (index >= interval.start && index <= interval.end) {
                return interval.initialization == null
                        ? Optional.empty()
                        : interval.initialization.find(brokenPath[1]);
            }
        }

        return Optional.empty();
    }

    /**
     * Returns a string representation of the variable value,
     * listing all intervals in order within square brackets.
     *
     * @return a string like {@code "[val1, 3(val2), val3]"}
     */
    @Override
    public String variableValue() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < intervals.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(intervals.get(i).toString());
        }
        return sb.append("]").toString();
    }

    /**
     * Creates a deep copy of this {@code RepeatedInitialization}.
     * Each interval's initialization is copied recursively.
     *
     * @return a new {@code RepeatedInitialization} with identical structure
     */
    @Override
    public RepeatedInitialization copy() {
        RepeatedInitialization copy = new RepeatedInitialization(dimension, null);
        for (Interval interval : intervals) {
            copy.put(interval.start + ".." + interval.end, interval.initialization.copy());
        }
        return copy;
    }

    /**
     * Compares this object with another for equality.
     * Two {@code RepeatedInitialization} instances are equal if they have
     * the same dimension and identical interval lists.
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepeatedInitialization)) return false;
        RepeatedInitialization other = (RepeatedInitialization) o;
        return dimension == other.dimension && intervals.equals(other.intervals);
    }

    /**
     * Returns a string representation of this object for debugging.
     *
     * @return a string like {@code "RepeatedInitialization{5, [val1, 2(val2)]}"}
     */
    @Override
    public String toString() {
        return "RepeatedInitialization{" + dimension + ", " + intervals + "}";
    }

    /**
     * Represents a contiguous interval within the array dimension,
     * associated with a single {@link Initialization}.
     * <p>
     * Intervals are immutable and used internally by {@code RepeatedInitialization}
     * to partition the dimension range.
     */
    public static class Interval {
        /** Inclusive start index of the interval. */
        public final int start;

        /** Inclusive end index of the interval. */
        public final int end;

        /** The initialization value for all indices in this interval. */
        public final Initialization initialization;

        /**
         * Constructs an interval with the given bounds and initialization.
         *
         * @param start          inclusive start index
         * @param end            inclusive end index
         * @param initialization the initialization for this interval (maybe {@code null})
         */
        public Interval(int start, int end, Initialization initialization) {
            this.start = start;
            this.end = end;
            this.initialization = initialization;
        }

        /**
         * Returns a compact string representation of this interval.
         * <p>
         * If the interval covers a single index, returns the initialization's
         * {@link Initialization#variableValue()}. Otherwise, returns
         * {@code "length(value)"} where length is {@code end - start + 1}.
         *
         * @return a string like {@code "value"} or {@code "3(value)"}
         */
        @Override
        public String toString() {
            String val = initialization != null ? initialization.variableValue() : "null";
            return start == end ? val : (end - start + 1) + "(" + val + ")";
        }

        /**
         * Compares this interval with another for equality.
         * Two intervals are equal if they have the same start, end, and initialization.
         *
         * @param o the object to compare with
         * @return {@code true} if the objects are equal, {@code false} otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Interval)) return false;
            Interval other = (Interval) o;
            return start == other.start && end == other.end && initialization.equals(other.initialization);
        }
    }
}
