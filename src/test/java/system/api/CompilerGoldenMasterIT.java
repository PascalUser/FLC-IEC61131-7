package system.api;

import compiler.FlcCompiler;
import compiler.GeneratedFile;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import compiler.repository.DiagnosticsHandler;
import utils.supports.CodeGeneratorTestSupport;
import utils.TestResources;
import compiler.repository.diagnostics.Diagnostic;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
public class CompilerGoldenMasterIT extends CodeGeneratorTestSupport {

    private static final String ROOT = "golden/luca/";

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void Parses_valid_reference_programs_without_syntax_errors(String example) throws Exception {
        try (Reader source = TestResources.reader(ROOT + example + "/" + example + ".fcl")) {
            parse(source);
        }
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void Generated_cpp_is_token_equivalent_to_the_golden_reference(String example) throws Exception {
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        List<GeneratedFile> generated;
        try (Reader source = TestResources.reader(ROOT + example + "/" + example + ".fcl")) {
            generated = new FlcCompiler(example, source).compile();
        }
        assertFalse(diagnostics.hasErrors(), () -> "The reference programs must compile without errors: "
                + messages(diagnostics));

        Map<String, String> actual = new TreeMap<>();
        for (GeneratedFile file : generated) {
            actual.put(file.name(), file.content());
        }
        Map<String, String> expected = new TreeMap<>();
        for (String name : Arrays.asList(example + "_out.cpp", example + "_out.h", example + "_outTB.cpp")) {
            expected.put(name, TestResources.read(ROOT + example + "/expected/" + name));
        }

        assertEquals(expected.keySet(), actual.keySet(), "Generated file names");
        for (Map.Entry<String, String> file : expected.entrySet()) {
            assertEquivalentCpp(file.getKey(), file.getValue(), actual.get(file.getKey()));
        }
    }

    private static String messages(DiagnosticsHandler diagnostics) {
        StringBuilder messages = new StringBuilder();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            messages.append(System.lineSeparator()).append(diagnostic.getMessage());
        }
        return messages.toString();
    }
}
