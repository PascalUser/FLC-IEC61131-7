package parser.utils;

import utils.enums.Subtype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Shape of a membership function as recognized by
 * {@link parser.facades.MembershipFunctionClassifier}: its subtype and the x coordinates that define it once
 * the redundant points are removed (e.g. {@code (0,1) (5,1) (10,0)} is {@code LINEAR} with {@code [5, 10]}).
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class MembershipFunctionShape {
    private final Subtype subtype;
    private final List<String> parameters;

    /**
     * Creates a shape.
     *
     * @param subtype    {@code SINGLETON}, {@code LINEAR}, {@code TRIANGLE}, {@code TRAPEZOID}, {@code COMPLEX}
     *                   or {@code VARIABLE}
     * @param parameters x coordinates (literal keys) that define the shape; {@code null} when the shape has none
     *                   ({@code COMPLEX}, {@code VARIABLE})
     */
    public MembershipFunctionShape(final Subtype subtype, final List<String> parameters) {
        this.subtype = Objects.requireNonNull(subtype, "subtype");
        this.parameters = (parameters == null) ? null : Collections.unmodifiableList(new ArrayList<>(parameters));
    }

    /**
     * Returns the subtype of the shape.
     *
     * @return the subtype
     */
    public Subtype subtype() {
        return subtype;
    }

    /**
     * Returns the x coordinates that define the shape.
     *
     * @return unmodifiable list of literal keys, or {@code null} for {@code COMPLEX} and {@code VARIABLE}
     */
    public List<String> parameters() {
        return parameters;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MembershipFunctionShape)) {
            return false;
        }
        MembershipFunctionShape that = (MembershipFunctionShape) other;
        return subtype == that.subtype && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtype, parameters);
    }

    @Override
    public String toString() {
        return subtype + (parameters == null ? "" : parameters.toString());
    }
}
