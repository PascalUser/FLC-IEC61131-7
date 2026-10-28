package integration.compiler;

import compiler.FlcCompiler;
import compiler.GeneratedFile;
import integration.lexer.parser.symboltable.NotYetImplemented;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.DiagnosticsHandler;
import utils.ParserTestSupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * End-to-end tests against the reference outputs of Luca Sarramone's FLC generator.
 * <p>
 * Each example lives in {@code src/test/resources/golden/luca/<base>/}: the FCL translation
 * ({@code <base>.fcl}) and the files his generator produces for the same controller ({@code expected/}).
 * The README of that folder explains where the files come from and how to regenerate them.
 * </p>
 * <p>
 * The generated code must be equivalent to the reference, not byte-identical: both are compared as sequences
 * of C++ tokens, ignoring whitespace, line breaks, comments and the case of letters (identifiers come from
 * case-insensitive IEC 61131 names, which the front-end normalizes to upper case).
 * </p>
 */
public class LucaGoldenIT extends ParserTestSupport {

    private static final String ROOT = "golden/luca/";
    private static final Pattern COMMENT = Pattern.compile("//[^\\n]*|/\\*.*?\\*/", Pattern.DOTALL);
    private static final Pattern TOKEN = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*|[0-9]+|\\S");
    private static final int CONTEXT_TOKENS = 12;

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void Fcl_translation_is_accepted_by_the_front_end(String example) throws Exception {
        try (Reader source = resource(example + "/" + example + ".fcl")) {
            parse(source);
        }
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    @NotYetImplemented
    void Generated_code_matches_the_reference_of_lucas_generator(String example) throws Exception {
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        List<GeneratedFile> generated;
        try (Reader source = resource(example + "/" + example + ".fcl")) {
            generated = new FlcCompiler(diagnostics).compile(source, example);
        }
        assertFalse(diagnostics.hasErrors(), "The reference programs must compile without errors");

        Map<String, String> actual = new TreeMap<>();
        for (GeneratedFile file : generated) {
            actual.put(file.name(), file.content());
        }
        Map<String, String> expected = new TreeMap<>();
        for (String name : Arrays.asList(example + "_out.cpp", example + "_out.h", example + "_outTB.cpp")) {
            expected.put(name, read(example + "/expected/" + name));
        }

        assertEquals(expected.keySet(), actual.keySet(), "Generated file names");
        for (Map.Entry<String, String> file : expected.entrySet()) {
            assertEquivalentCpp(file.getKey(), file.getValue(), actual.get(file.getKey()));
        }
    }

    private static void assertEquivalentCpp(String fileName, String expected, String actual) {
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

    private static Reader resource(String path) {
        InputStream stream = LucaGoldenIT.class.getClassLoader().getResourceAsStream(ROOT + path);
        assertNotNull(stream, "Missing test resource " + ROOT + path);
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    private static String read(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[8192];
        try (Reader reader = resource(path)) {
            int count;
            while ((count = reader.read(buffer)) != -1) {
                content.append(buffer, 0, count);
            }
        }
        return content.toString();
    }
}
