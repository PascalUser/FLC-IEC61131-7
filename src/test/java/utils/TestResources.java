package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Reads files from {@code src/test/resources}.
 */
public final class TestResources {

    private TestResources() {
    }

    /**
     * Opens a test resource as UTF-8 text.
     *
     * @param path path relative to {@code src/test/resources}
     * @return a reader the caller must close
     */
    public static Reader reader(String path) {
        InputStream stream = TestResources.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "Missing test resource " + path);
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    /**
     * Reads a whole test resource as UTF-8 text.
     *
     * @param path path relative to {@code src/test/resources}
     * @return the content of the file
     * @throws IOException if the resource cannot be read
     */
    public static String read(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[8192];
        try (Reader reader = reader(path)) {
            int count;
            while ((count = reader.read(buffer)) != -1) {
                content.append(buffer, 0, count);
            }
        }
        return content.toString();
    }
}
