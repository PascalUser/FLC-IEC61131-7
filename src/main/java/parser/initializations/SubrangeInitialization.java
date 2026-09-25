package parser.initializations;

import java.util.Objects;

public final class SubrangeInitialization implements Initialization {
    private final String ilimit;
    private final String slimit;

    public SubrangeInitialization(String ilimit, String slimit) {
        this.ilimit = ilimit;
        this.slimit = slimit;
    }

    @Override
    public Initialization selectVariable(String variable) {
        return this;
    }

    @Override
    public String getVariableValue() {
        return ilimit;
    }

    @Override
    public Initialization copy() {
        return new SubrangeInitialization(ilimit, slimit);
    }

    /**
     * Value-object equality based on the wrapped value. Unlike
     * {@link SubrangeInitialization} (which has no state of its own and is
     * always "the default value"), a {@code SubrangeInitialization}'s
     * identity as a value *is* {@code ilimit} and {@code slimit} — two instances
     * are equal only when they hold the same valueS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubrangeInitialization)) {
            return false;
        }
        SubrangeInitialization other = (SubrangeInitialization) o;
        return Objects.equals(this.ilimit, other.ilimit)
            && Objects.equals(this.slimit, other.slimit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ilimit + slimit);
    }

    @Override
    public String toString() {
        return "SubrangeInitialization{" + ilimit + ".." + slimit + "}";
    }
}
