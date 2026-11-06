package compiler.parser.initializations.leafs;

import compiler.parser.initializations.Initialization;

import java.util.Optional;

/**
 * Abstract base class for leaf (non-composite) initializations.
 * <p>
 * Leaf initializations represent atomic values such as integers, reals,
 * booleans, strings, enumerated values, and subrange bounds. They have
 * no children, so {@link #find(String)} only resolves for the empty path.
 * All leaf initializations are immutable and return {@code this} from {@link #copy()}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see VariableInitialization
 * @see EnumeratedInitialization
 * @see DefaultInitialization
 * @see SubrangeInitialization
 */
public abstract class LeafInitialization implements Initialization {

    /** {@inheritDoc} */
    @Override
    public final Optional<Initialization> find(final String path) {
        return path.isEmpty() ? Optional.of(this) : Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public Initialization copy() {
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public abstract boolean equals(Object o);

    /** {@inheritDoc} */
    @Override
    public abstract int hashCode();

    /** {@inheritDoc} */
    @Override
    public abstract String toString();
}
