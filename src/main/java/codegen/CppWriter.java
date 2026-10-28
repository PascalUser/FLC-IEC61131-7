package codegen;

/**
 * In-memory text sink for the generated C++ code.
 * <p>
 * Replaces the static {@code Writer.file} of Luca Sarramone's generator: each generated file gets its own
 * writer, so generation has no global state and no I/O.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class CppWriter {
    private final StringBuilder text = new StringBuilder();

    /**
     * Appends a fragment of code exactly as given.
     *
     * @param fragment the code to append
     */
    public void write(final String fragment) {
        text.append(fragment);
    }

    /**
     * Returns everything written so far.
     *
     * @return the generated text
     */
    @Override
    public String toString() {
        return text.toString();
    }
}
