package parser.initializations.nodes;

import parser.initializations.Initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repeated initialization based on a list of disjoint,
 * contiguous intervals spanning an array dimension.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class RepeatedInitialization extends NodeInitialization {
    private final int dimension;
    private final List<Interval> intervals;

    public RepeatedInitialization(int dimension, Initialization defaultValue) {
        this.dimension = dimension;
        this.intervals = new ArrayList<>();
        this.intervals.add(new Interval(0, dimension - 1, defaultValue));
    }

    @Override
    public Initialization put(final String path, final Initialization initialization) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            throw new RuntimeException("RepeatedInitialization: cannot insert an element in the root");
        }

        String targetStr = brokenPath[0];
        String childPath = brokenPath[1];

        // Determinar inicio y fin (soporta tanto "0..3" como "2")
        int start, end;
        if (targetStr.contains("..")) {
            String[] parts = targetStr.split("\\.\\.");
            start = Integer.parseInt(parts[0].trim());
            end = Integer.parseInt(parts[1].trim());
        } else {
            start = Integer.parseInt(targetStr.trim());
            end = start; // Un índice suelto es un rango de tamaño 1
        }

        int effectiveStart = Math.max(0, start);
        int effectiveEnd = Math.min(dimension - 1, end);

        if (effectiveStart > effectiveEnd) {
            return null; // Or the default initialization you consider if out of range
        }

        List<Interval> newIntervals = new ArrayList<>();
        Initialization oldInitialization = null;

        for (Interval interval : intervals) {
            // Fuera del rango
            if (interval.end < effectiveStart || interval.start > effectiveEnd) {
                newIntervals.add(interval);
            } else {
                // Conservar extremo izquierdo intacto
                if (interval.start < effectiveStart) {
                    newIntervals.add(new Interval(interval.start, effectiveStart - 1, interval.initialization));
                }

                // Determinar la zona de solapamiento (lo que vamos a modificar)
                int overlapStart = Math.max(interval.start, effectiveStart);
                int overlapEnd = Math.min(interval.end, effectiveEnd);

                // Guardamos el primer valor viejo afectado para el return (Punto 3)
                if (oldInitialization == null) {
                    oldInitialization = interval.initialization;
                }

                Initialization newInitForOverlap;
                if (childPath.isEmpty()) {
                    // Reemplazo completo (Punto 2 - sin ruta anidada)
                    newInitForOverlap = initialization;
                } else {
                    // Delegación a la ruta anidada (Punto 2 - ej. 0..3#X)
                    if (interval.initialization == null) {
                        throw new RuntimeException(
                                "RepeatedInitialization: element is null, cannot delegate " + childPath
                        );
                    }
                    // IMPORTANTE: Clonar para no alterar referencias si el intervalo original era más grande
                    newInitForOverlap = interval.initialization.copy();
                    NodeInitialization childNode = (NodeInitialization) newInitForOverlap;
                    childNode.put(childPath, initialization);
                }

                newIntervals.add(new Interval(overlapStart, overlapEnd, newInitForOverlap));

                // Conservar extremo derecho intacto
                if (interval.end > effectiveEnd) {
                    newIntervals.add(new Interval(effectiveEnd + 1, interval.end, interval.initialization));
                }
            }
        }

        this.intervals.clear();
        this.intervals.addAll(newIntervals);

        return oldInitialization;
    }

    @Override
    public Optional<Initialization> find(final String path) {
        String[] brokenPath = breakStringPath(path);

        if (brokenPath[0].isEmpty()) {
            return Optional.of(this);
        }

        String targetStr = brokenPath[0];
        int index;

        try {
            // Si nos piden buscar un rango "0..3", buscamos basándonos en el primer elemento
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

        // Delegar la búsqueda (Punto 1 resuelto)
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
        RepeatedInitialization copy = new RepeatedInitialization(dimension, null);
        for (Interval interval : intervals) {
            copy.put(interval.start + ".." + interval.end, interval.initialization.copy());
        }
        return copy;
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
        return "RepeatedInitialization{" + dimension + ", " + intervals + "}";
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
