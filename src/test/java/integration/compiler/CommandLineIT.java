package integration.compiler;

import compiler.CommandLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utils.TestResources;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.CppAssertions.assertEquivalentCpp;

/**
 * Integration tests of the command line: arguments, files written, diagnostics and exit status.
 */
public class CommandLineIT {

    private static final String PENDULUM = "golden/luca/pendulo_invertido/";

    @TempDir
    Path directory;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private int run(String... args) throws Exception {
        try (PrintStream outStream = new PrintStream(out, true, "UTF-8");
             PrintStream errStream = new PrintStream(err, true, "UTF-8")) {
            return CommandLine.run(args, outStream, errStream);
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
    void Program_is_compiled_next_to_itself_with_its_name_as_base_name() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        int status = run(program.toString());

        assertEquals(CommandLine.OK, status, errors());
        for (String name : new String[] {"pendulo_invertido_out.cpp", "pendulo_invertido_out.h",
                "pendulo_invertido_outTB.cpp"}) {
            assertEquivalentCpp(name, TestResources.read(PENDULUM + "expected/" + name),
                    read(directory.resolve(name)));
        }
    }

    @Test
    void Output_directory_can_be_given_and_is_created() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));
        Path output = directory.resolve("generated/hls");

        int status = run(program.toString(), output.toString());

        assertEquals(CommandLine.OK, status, errors());
        assertTrue(Files.exists(output.resolve("pendulo_invertido_out.cpp")));
    }

    @Test
    void Spaces_in_the_program_name_become_underscores() throws Exception {
        Path program = write("pendulo invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        run(program.toString());

        assertTrue(Files.exists(directory.resolve("pendulo_invertido_outTB.cpp")));
        assertTrue(read(directory.resolve("pendulo_invertido_outTB.cpp")).contains("\"pendulo_invertido_out.h\""));
    }

    @Test
    void Warnings_are_printed_and_the_code_is_generated() throws Exception {
        Path program = write("pendulo_invertido.fcl", TestResources.read(PENDULUM + "pendulo_invertido.fcl"));

        run(program.toString());

        assertTrue(errors().contains("DEFAULT of FUERZA is ignored"), errors());
    }

    @Test
    void Unsupported_program_writes_nothing_and_explains_why() throws Exception {
        String withOr = TestResources.read(PENDULUM + "pendulo_invertido.fcl")
                .replace("RULE 1 : IF angulo IS nl AND", "RULE 1 : IF angulo IS nl OR");
        Path program = write("pendulo_invertido.fcl", withOr);

        int status = run(program.toString());

        assertEquals(CommandLine.COMPILATION_ERRORS, status);
        assertTrue(errors().contains("rule 1 of PENDULO_INVERTIDO#REGLAS uses OR"), errors());
        assertFalse(Files.exists(directory.resolve("pendulo_invertido_out.cpp")));
    }

    @Test
    void Syntax_error_writes_nothing() throws Exception {
        Path program = write("broken.fcl", "FUNCTION_BLOCK broken VAR_INPUT x : USINT; END_FUNCTION_BLOCK");

        int status = run(program.toString());

        assertEquals(CommandLine.COMPILATION_ERRORS, status);
        assertFalse(Files.exists(directory.resolve("broken_out.cpp")));
    }

    @Test
    void Missing_program_is_a_usage_error() throws Exception {
        int status = run(directory.resolve("missing.fcl").toString());

        assertEquals(CommandLine.USAGE_ERROR, status);
        assertTrue(errors().contains("Cannot read"), errors());
    }

    @Test
    void Wrong_number_of_arguments_prints_the_usage() throws Exception {
        int status = run();

        assertEquals(CommandLine.USAGE_ERROR, status);
        assertTrue(errors().startsWith("Usage:"), errors());
    }
}
