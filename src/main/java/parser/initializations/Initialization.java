package parser.initializations;

import java.util.Optional;

/**
 * Represents a variable initialization value in the IEC 61131-7 parser.
 * <p>
 * Initializations form a tree structure where leaf nodes hold literal values
 * (integers, reals, booleans, strings, enumerated values, subranges) and
 * internal nodes represent structured types (structs, repeated arrays).
 * The {@link #find(String)} method allows path-based navigation using
 * {@code #} as a separator (e.g., {@code "RGB#R"} or {@code "2#X"}).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see parser.initializations.leafs.LeafInitialization
 * @see parser.initializations.nodes.NodeInitialization
 */
public interface Initialization {

    /**
     * Looks up a nested initialization by path, without modifying anything.
     * <p>
     * A path is a sequence of field names (structs) or indices (arrays) separated by
     * {@code '#'}, e.g. {@code "RGB#GAMMA_R"} or {@code "2#X"}. The empty path designates
     * this very initialization. Leaf initializations have no children, so for them only the
     * empty path resolves.
     *
     * @param path the path to the nested initialization; {@code ""} for this one
     * @return the initialization found at {@code path}, or empty if the path does not exist
     */
    Optional<Initialization> find(String path);

    /**
     * Returns the string representation of this initialization's value.
     */
    String variableValue();

    /**
     * Creates a copy of this initialization that can be modified independently.
     * <p>
     * Immutable initializations may return {@code this}.
     * </p>
     *
     * @return an independent copy (or {@code this} if immutable)
     */
    Initialization copy();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();
}