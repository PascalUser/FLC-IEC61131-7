package compiler;

import java.util.Objects;

/**
 * A file produced by the compiler: its name (without directory) and its whole text content.
 * <p>
 * Kept in memory so that callers decide where to write it and tests can compare it directly.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class GeneratedFile {
    private final String name;
    private final String content;

    /**
     * Creates a generated file.
     *
     * @param name    file name, e.g. {@code pendulo_invertido_out.cpp}
     * @param content full text of the file
     */
    public GeneratedFile(final String name, final String content) {
        this.name = Objects.requireNonNull(name, "name");
        this.content = Objects.requireNonNull(content, "content");
    }

    /**
     * Returns the file name.
     *
     * @return the file name, without directory
     */
    public String name() {
        return name;
    }

    /**
     * Returns the file content.
     *
     * @return the full text of the file
     */
    public String content() {
        return content;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeneratedFile)) {
            return false;
        }
        GeneratedFile that = (GeneratedFile) other;
        return name.equals(that.name) && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

    @Override
    public String toString() {
        return "GeneratedFile{" + name + ", " + content.length() + " chars}";
    }
}
