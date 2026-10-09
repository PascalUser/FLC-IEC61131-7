package parser.initializations.nodes;

import parser.initializations.Initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repeated initialization based on a list of disjoint,
 * contiguous intervals spanning an array dimension.
 */
public final class RepeatedInitialization extends NodeInitialization {
    private final int dimension;
    private final List<Interval> intervals;

    public RepeatedInitialization(int dimension, List<Interval> intervals) {
        this.dimension = dimension;
        this.intervals = new ArrayList<>(intervals);
    }

    /**
     * Replaces or updates the initialization at the specified index or nested path.
     * <p>
     * If the target index belongs to an interval spanning multiple elements, the interval
     * is automatically split so that only the targeted index is mutated.
     * </p>
     *
     * @param path           the index, optionally followed by {@code #} and a nested path
     *                       (e.g. {@code "2"} or {@code "2#X"})
     * @param initialization the new initialization value to set
     * @return the previous initialization value at the target position/path
     * @throws RuntimeException if the path or index is invalid
     */
    public Initialization put(final String path, final Initialization initialization) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            throw new RuntimeException("RepeatedInitialization: cannot insert an element in the root");
        }

        int index;
        try {
            index = Integer.parseInt(brokenPath[0]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("RepeatedInitialization: index is not a valid number: " + brokenPath[0]);
        }

        if (index < 0 || index >= dimension) {
            throw new RuntimeException("RepeatedInitialization: index out of bounds: " + index);
        }

        // Buscar el intervalo que contiene al índice objetivo
        int targetIntervalIdx = -1;
        Interval targetInterval = null;
        for (int i = 0; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            if (index >= interval.start && index <= interval.end) {
                targetIntervalIdx = i;
                targetInterval = interval;
                break;
            }
        }

        if (targetInterval == null) {
            throw new RuntimeException("RepeatedInitialization: no interval found for index " + index);
        }

        // Preparar la división (split) del intervalo en caso de que cubra múltiples elementos
        List<Interval> replacements = new ArrayList<>();

        if (targetInterval.start < index) {
            replacements.add(new Interval(targetInterval.start, index - 1, targetInterval.initialization));
        }

        Initialization oldInit = targetInterval.initialization;
        Initialization elementInit;

        if (brokenPath[1].isEmpty()) {
            elementInit = initialization;
            replacements.add(new Interval(index, index, elementInit));
        } else {
            if (oldInit == null) {
                throw new RuntimeException("RepeatedInitialization: element at index " + index + " is null");
            }
            // Si el intervalo abarcaba varios índices, se clona el objeto para no mutar los demás elementos
            elementInit = (targetInterval.start == targetInterval.end) ? oldInit : oldInit.copy();
            replacements.add(new Interval(index, index, elementInit));
        }

        if (targetInterval.end > index) {
            replacements.add(new Interval(index + 1, targetInterval.end, targetInterval.initialization));
        }

        // Reemplazar el intervalo original por los subintervalos fragmentados
        intervals.remove(targetIntervalIdx);
        intervals.addAll(targetIntervalIdx, replacements);

        // Si la ruta terminaba en el índice, se retorna la inicialización previa
        if (brokenPath[1].isEmpty()) {
            return oldInit;
        }

        // Si la ruta continúa (ej. "2#X"), se delega la inserción al nodo hijo
        NodeInitialization childNode = (NodeInitialization) elementInit;
        return childNode.put(brokenPath[1], initialization);
    }

    /**
     * Finds the initialization stored at an index, optionally followed by a path into it
     * (e.g. {@code "2"} or {@code "2#X"} when the elements are structs).
     *
     * @param path the index, optionally followed by {@code #} and a path into the element
     * @return the element initialization, or empty if the index is not a valid position
     */
    @Override
    public Optional<Initialization> find(final String path) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            return Optional.of(this);
        }

        int index;
        try {
            index = Integer.parseInt(brokenPath[0]);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        if (index < 0 || index >= dimension) {
            return Optional.empty();
        }
        for (Interval interval : intervals) {
            if (index >= interval.start && index <= interval.end) {
                return interval.initialization == null
                        ? Optional.empty()
                        : interval.initialization.find(brokenPath[1]);
            }
        }
        return Optional.empty();
    }

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

    @Override
    public RepeatedInitialization copy() {
        List<Interval> copiedIntervals = new ArrayList<>();
        for (Interval interval : intervals) {
            copiedIntervals.add(new Interval(
                    interval.start,
                    interval.end,
                    interval.initialization != null ? interval.initialization.copy() : null
            ));
        }
        return new RepeatedInitialization(dimension, copiedIntervals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepeatedInitialization)) return false;
        RepeatedInitialization other = (RepeatedInitialization) o;
        return dimension == other.dimension && intervals.equals(other.intervals);
    }

    @Override
    public String toString() {
        return "RepeatedInitialization{" + dimension + ", " + intervals;
    }

    public static class Interval {
        public final int start;
        public final int end;
        public final Initialization initialization;

        public Interval(int start, int end, Initialization initialization) {
            this.start = start;
            this.end = end;
            this.initialization = initialization;
        }

        @Override
        public String toString() {
            String val = initialization != null ? initialization.variableValue() : "null";
            return start == end ? val : (end - start + 1) + "(" + val + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Interval)) return false;
            Interval other = (Interval) o;
            return start == other.start && end == other.end && initialization.equals(other.initialization);
        }
    }
}