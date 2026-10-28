package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Assertions on generated C++ code.
 */
public final class CppAssertions {
    private static final Pattern COMMENT = Pattern.compile("//[^\\n]*|/\\*.*?\\*/", Pattern.DOTALL);
    private static final Pattern TOKEN = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*|[0-9]+|\\S");
    private static final int CONTEXT_TOKENS = 12;

    private CppAssertions() {
    }

    /**
     * Asserts that two C++ sources are equivalent: the same sequence of tokens, ignoring whitespace, line
     * breaks, comments and the case of letters (IEC 61131 names are case-insensitive and the front-end
     * normalizes them to upper case). On failure the message shows the first different token and what precedes
     * it.
     *
     * @param fileName name of the file, for the message
     * @param expected reference code
     * @param actual   generated code
     */
    public static void assertEquivalentCpp(String fileName, String expected, String actual) {
        List<String> expectedTokens = tokens(expected);
        List<String> actualTokens = tokens(actual);
        int common = Math.min(expectedTokens.size(), actualTokens.size());
        for (int i = 0; i < common; i++) {
            if (!expectedTokens.get(i).equals(actualTokens.get(i))) {
                fail(fileName + ": first difference at token " + i + ": expected '" + expectedTokens.get(i)
                        + "' but was '" + actualTokens.get(i) + "' after: " + context(expectedTokens, i));
            }
        }
        assertEquals(expectedTokens.size(), actualTokens.size(), fileName + ": number of tokens");
    }

    /**
     * Asserts that two texts are identical except for line endings ({@code \r\n} vs {@code \n}).
     *
     * @param fileName name of the file, for the message
     * @param expected reference text
     * @param actual   generated text
     */
    public static void assertSameText(String fileName, String expected, String actual) {
        assertEquals(expected.replace("\r\n", "\n"), actual.replace("\r\n", "\n"), fileName);
    }

    private static List<String> tokens(String cpp) {
        String withoutComments = COMMENT.matcher(cpp).replaceAll(" ");
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN.matcher(withoutComments);
        while (matcher.find()) {
            tokens.add(matcher.group().toLowerCase(Locale.ROOT));
        }
        return tokens;
    }

    private static String context(List<String> tokens, int index) {
        return String.join(" ", tokens.subList(Math.max(0, index - CONTEXT_TOKENS), index));
    }
}
