package parser.initializations.nodes;

import parser.initializations.Initialization;

/**
 * Abstract base class for composite initializations (structs and repeated arrays).
 * <p>
 * Node initializations contain child initializations and support path-based
 * insertion and lookup via the {@link #put(String, Initialization)} and
 * {@link #find(String)} methods. The {@link #breakStringPath(String)} utility
 * splits a path at the first {@code #} into a two-element array.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see StructInitialization
 * @see RepeatedInitialization
 */
public abstract class NodeInitialization implements Initialization {
    public abstract Initialization put(String path, Initialization initialization);

    protected static String[] breakStringPath(final String path) {
        String[] brokenStringPath = new String[2];

        if (path.isEmpty()) {
            brokenStringPath[0] = "";
            brokenStringPath[1] = "";
            return brokenStringPath;
        }

        int octothorpeIdx = path.indexOf('#');
        if (octothorpeIdx == -1) {
            brokenStringPath[0] = path;
            brokenStringPath[1] = "";
        } else {
            brokenStringPath[0] = path.substring(0, octothorpeIdx);
            brokenStringPath[1] = path.substring(octothorpeIdx + 1);
        }
        return brokenStringPath;
    }
}
