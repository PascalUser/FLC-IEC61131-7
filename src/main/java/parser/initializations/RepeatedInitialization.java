package parser.initializations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a repeated initialization based on a list of disjoint,
 * contiguous intervals that partition the range [0, dimension - 1].
 * <p>
 * Each interval directly knows its [start, end] boundaries, rather than
 * deriving them from an accumulated repetition counter. This simplifies
 * the insertion of a new interval: existing overlapping intervals are
 * simply cropped (or removed if entirely covered), and the new interval
 * is inserted exactly once in its corresponding position.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.1
 * @since 1.0
 */
public class RepeatedInitialization implements Initialization {

    /**
     * A disjoint and contiguous interval [start, end] with its associated
     * initialization object (which can be null).
     */
    private static class Interval {
        private final int start;
        private int end;
        private final Initialization initialization;

        private Interval(int start, int end, Initialization initialization) {
            this.start = start;
            this.end = end;
            this.initialization = initialization;
        }

        private int length() {
            return end - start + 1;
        }

        private boolean overlaps(int otherStart, int otherEnd) {
            return start <= otherEnd && end >= otherStart;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Interval)) {
                return false;
            }
            Interval other = (Interval) o;
            return this.start == other.start &&
                    this.end == other.end &&
                    Objects.equals(this.initialization, other.initialization);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, initialization);
        }

        @Override
        public String toString() {
            return "[" + start + ".." + end + "]=" + initialization.toString();
        }
    }

    private final List<Interval> intervals;
    private final int dimension;

    /**
     * Initializes the object spanning the full range (0 to dimension - 1)
     * with a single default initialization interval.
     *
     * @param dimension             the total size of the array dimension
     * @param defaultInitialization the default initialization to apply across the entire dimension
     */
    public RepeatedInitialization(int dimension, Initialization defaultInitialization) {
        this.dimension = Math.max(0, dimension);
        this.intervals = new ArrayList<>();
        if (this.dimension > 0) {
            this.intervals.add(new Interval(0, this.dimension - 1, defaultInitialization));
        }
    }

    /**
     * Adds or overwrites an interval of indices [start, end] with a new initialization.
     * Boundaries exceeding [0, dimension - 1] are safely clamped without throwing exceptions.
     *
     * @param start          the starting index (inclusive)
     * @param end            the ending index (inclusive)
     * @param initialization the initialization object for this interval
     */
    public void addInterval(int start, int end, Initialization initialization) {
        if (dimension <= 0 || start > end || start >= dimension || end < 0) {
            return;
        }

        // Clamp boundaries to the valid range [0, dimension - 1]
        int s = Math.max(0, start);
        int e = Math.min(dimension - 1, end);

        List<Interval> result = new ArrayList<>();
        boolean inserted = false;

        for (Interval interval : intervals) {
            if (!interval.overlaps(s, e)) {
                if (!inserted && interval.start > e) {
                    result.add(new Interval(s, e, initialization));
                    inserted = true;
                }
                result.add(interval);
                continue;
            }

            // Overlap detected: crop the original interval.
            if (interval.start < s) {
                result.add(new Interval(interval.start, s - 1, interval.initialization));
            }
            if (!inserted) {
                result.add(new Interval(s, e, initialization));
                inserted = true;
            }
            if (interval.end > e) {
                result.add(new Interval(e + 1, interval.end, interval.initialization));
            }
            // If the original interval is entirely covered by [s, e],
            // no remainder is added, effectively overwriting it completely.
        }

        if (!inserted) {
            result.add(new Interval(s, e, initialization));
        }

        this.intervals.clear();
        this.intervals.addAll(compact(result));
    }

    /**
     * Merges adjacent intervals that share the exact same initialization
     * to keep the underlying list optimized.
     *
     * @param input the uncompacted list of intervals
     * @return a new compacted list of intervals
     */
    private List<Interval> compact(List<Interval> input) {
        List<Interval> result = new ArrayList<>();
        for (Interval interval : input) {
            if (interval.length() <= 0) {
                continue;
            }
            if (!result.isEmpty()) {
                Interval last = result.get(result.size() - 1);
                if (last.end + 1 == interval.start && Objects.equals(last.initialization, interval.initialization)) {
                    last.end = interval.end;
                    continue;
                }
            }
            result.add(interval);
        }
        return result;
    }

    @Override
    public Initialization selectVariable(String variable) {
        try {
            int index = Integer.parseInt(variable);
            if (index < 0 || index >= dimension) {
                return null;
            }
            for (Interval interval : intervals) {
                if (index >= interval.start && index <= interval.end) {
                    return interval.initialization != null ? interval.initialization.selectVariable(variable) : null;
                }
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    @Override
    public String getVariableValue() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            String val = interval.initialization != null ? interval.initialization.getVariableValue() : "null";
            sb.append(interval.length()).append("x").append(val);
            if (i < intervals.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    @Override
    public Initialization copy() {
        // Initialize with null since we will manually rebuild and override the intervals.
        RepeatedInitialization copy = new RepeatedInitialization(this.dimension, null);
        copy.intervals.clear();
        for (Interval interval : this.intervals) {
            Initialization initCopy = interval.initialization != null ? interval.initialization.copy() : null;
            copy.intervals.add(new Interval(interval.start, interval.end, initCopy));
        }
        return copy;
    }

    /**
     * Retrieves the total dimension of the array.
     *
     * @return the total dimension
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Retrieves a list containing the lengths of all current intervals.
     * Intended primarily for testing and inspection.
     *
     * @return a list of interval repetitions
     */
    public List<Integer> getRepetitionsList() {
        List<Integer> reps = new ArrayList<>();
        for (Interval interval : intervals) {
            reps.add(interval.length());
        }
        return reps;
    }

    /**
     * Retrieves a list of the initialization objects corresponding to each interval.
     * Intended primarily for testing and inspection.
     *
     * @return a list of initialization objects
     */
    public List<Initialization> getInitializationsList() {
        List<Initialization> inits = new ArrayList<>();
        for (Interval interval : intervals) {
            inits.add(interval.initialization);
        }
        return inits;
    }

    /**
     * Value-object equality based on the exact layout of intervals and the total dimension.
     * Two instances are equal only when they have the same dimension and exactly
     * the same partitioned segments with identical initializations.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepeatedInitialization)) {
            return false;
        }
        RepeatedInitialization other = (RepeatedInitialization) o;
        return this.dimension == other.dimension &&
                Objects.equals(this.intervals, other.intervals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, intervals);
    }

    @Override
    public String toString() {
        return "RepeatedInitialization{" +
                "dimension=" + dimension +
                ", intervals=" + intervals +
                '}';
    }
}