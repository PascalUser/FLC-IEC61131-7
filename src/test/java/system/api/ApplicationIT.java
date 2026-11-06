package system.api;

import api.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utils.TestResources;
import utils.supports.CodeGeneratorTestSupport;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests of the command line: arguments, files written, diagnostics and exit status.
 */
public class ApplicationIT extends CodeGeneratorTestSupport {

    private static final String PENDULUM = "golden/luca/pendulo_invertido/";

    @TempDir
    Path directory;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private int run(String... args) throws Exception {
        try (PrintStream outStream = new PrintStream(out, true, "UTF-8");
            PrintStream errStream = new PrintStream(err, true, "UTF-8")) {
            return Application.run(args, outStream, errStream);
        }
    }

    private Path write(String name, String content) throws Exception {
        Path file = directory.resolve(name);
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
        return file;
    }

    private static String read(Path file) throws Exception {
        return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
    }

    private String errors() throws Exception {
        return err.toString("UTF-8");
    }

    @Test
    void Compiles_successfully_and_generates_files_in_same_directory() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        int status = run(program.toString());

        assertEquals(Application.CODE_OK, status, errors());
        for (String name : new String[] {"pendulo_invertido_out.cpp", "pendulo_invertido_out.h",
                "pendulo_invertido_outTB.cpp"}) {
            assertEquivalentCpp(name, TestResources.read(PENDULUM + "expected/" + name),
                    read(directory.resolve(name)));
        }
    }

    @Test
    void Creates_and_populates_custom_output_directory_when_provided() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));
        Path output = directory.resolve("generated/hls");

        int status = run(program.toString(), output.toString());

        assertEquals(Application.CODE_OK, status, errors());
        assertTrue(Files.exists(output.resolve("pendulo_invertido_out.cpp")));
    }

    @Test
    void Replaces_spaces_with_underscores_in_generated_filenames() throws Exception {
        Path program = write("pendulo invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        run(program.toString());

        assertTrue(Files.exists(directory.resolve("pendulo_invertido_outTB.cpp")));
        assertTrue(read(directory.resolve("pendulo_invertido_outTB.cpp")).contains("\"pendulo_invertido_out.h\""));
    }

    @Test
    void Prints_warnings_to_stderr_but_still_generates_code_successfully() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        run(program.toString());

        assertTrue(errors().contains("DEFAULT of FUERZA is ignored"), errors());
    }

    @Test
    void Aborts_with_compiler_error_code_and_explains_unsupported_features() throws Exception {
        String withOr = TestResources.read(PENDULUM + "pendulo_invertido.fcl")
                .replace("RULE 1 : IF angulo IS nl AND", "RULE 1 : IF angulo IS nl OR");
        Path program = write("pendulo_invertido.fcl", withOr);

        int status = run(program.toString());

        assertEquals(Application.CODE_COMPILER_ERR, status);
        assertTrue(errors().contains("rule 1 of REGLAS uses OR"), errors());
        assertFalse(Files.exists(directory.resolve("pendulo_invertido_out.cpp")));
    }

    @Test
    void Aborts_with_compiler_error_code_without_generating_files_on_syntax_error() throws Exception {
        Path program = write("broken.fcl", "FUNCTION_BLOCK broken VAR_INPUT x : USINT; END_FUNCTION_BLOCK");

        int status = run(program.toString());

        assertEquals(Application.CODE_COMPILER_ERR, status);
        assertFalse(Files.exists(directory.resolve("broken_out.cpp")));
    }

    @Test
    void Fails_with_argument_error_when_input_file_is_missing() throws Exception {
        int status = run(directory.resolve("missing.fcl").toString());

        assertEquals(Application.CODE_ARGUMENT_ERR, status);
        assertTrue(errors().contains("Cannot read"), errors());
    }

    @Test
    void Fails_with_argument_error_and_prints_usage_on_empty_arguments() throws Exception {
        int status = run();

        assertEquals(Application.CODE_ARGUMENT_ERR, status);
        assertTrue(errors().startsWith("Usage:"), errors());
    }
}
