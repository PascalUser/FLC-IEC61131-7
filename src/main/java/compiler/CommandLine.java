package compiler;

import utils.DiagnosticsHandler;
import utils.diagnostics.Diagnostic;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command line of the compiler: {@code <program.fcl> [output directory]}.
 * <p>
 * Compiles the program with {@link FlcCompiler}, prints every diagnostic to the error stream and writes the
 * generated files to the output directory (by default, the directory of the program). The base name of the files
 * is the name of the program without extension, with spaces replaced by {@code _}, as in Luca Sarramone's
 * generator: {@code pendulo invertido.fcl} gives {@code pendulo_invertido_out.cpp}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class CommandLine {
    /** The code was generated (there may be warnings). */
    public static final int OK = 0;
    /** The program has errors or uses something the generator does not support; nothing was written. */
    public static final int COMPILATION_ERRORS = 1;
    /** Wrong arguments, or a file could not be read or written. */
    public static final int USAGE_ERROR = 2;
    /** Unexpected failure of the compiler itself. */
    public static final int INTERNAL_ERROR = 3;

    private static final String USAGE = String.join(System.lineSeparator(),
            "Usage: java -jar flc-iec61131-7-compiler.jar <program.fcl> [output directory]",
            "Compiles an IEC 61131-7 FCL program into Vivado HLS C++:",
            "<base>_out.cpp, <base>_out.h and <base>_outTB.cpp (by default next to the program).");

    private CommandLine() {
        // Utility class - not instantiable
    }

    /**
     * Runs the compiler.
     *
     * @param args command-line arguments: the program and, optionally, the output directory
     * @param out  stream for progress messages
     * @param err  stream for diagnostics and errors
     * @return the exit status: {@link #OK}, {@link #COMPILATION_ERRORS}, {@link #USAGE_ERROR} or
     *         {@link #INTERNAL_ERROR}
     */
    public static int run(final String[] args, final PrintStream out, final PrintStream err) {
        if (args.length < 1 || args.length > 2) {
            err.println(USAGE);
            return USAGE_ERROR;
        }
        Path program = Paths.get(args[0]).toAbsolutePath();
        Path outputDirectory = (args.length == 2) ? Paths.get(args[1]) : program.getParent();
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        List<GeneratedFile> files;
        try (Reader source = Files.newBufferedReader(program, StandardCharsets.UTF_8)) {
            files = new FlcCompiler(diagnostics).compile(source, baseName(program));
        } catch (IOException e) {
            err.println("Cannot read " + program + ": " + e.getMessage());
            return USAGE_ERROR;
        } catch (RuntimeException e) {
            printDiagnostics(diagnostics, err);
            err.println("Internal compiler error, please report it with the program: " + e);
            e.printStackTrace(err);
            return INTERNAL_ERROR;
        }
        printDiagnostics(diagnostics, err);
        if (files.isEmpty()) {
            err.println("No code generated: " + program.getFileName() + " has errors");
            return COMPILATION_ERRORS;
        }
        try {
            Files.createDirectories(outputDirectory);
            for (GeneratedFile file : files) {
                Path target = outputDirectory.resolve(file.name());
                Files.write(target, file.content().getBytes(StandardCharsets.UTF_8));
                out.println("Generated " + target);
            }
        } catch (IOException e) {
            err.println("Cannot write to " + outputDirectory + ": " + e.getMessage());
            return USAGE_ERROR;
        }
        return OK;
    }

    /** Name of the program without extension and with spaces replaced by {@code _}. */
    private static String baseName(final Path program) {
        String name = String.valueOf(program.getFileName());
        int dot = name.lastIndexOf('.');
        return ((dot > 0) ? name.substring(0, dot) : name).replace(' ', '_');
    }

    private static void printDiagnostics(final DiagnosticsHandler diagnostics, final PrintStream err) {
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            err.println(diagnostic.getMessage());
        }
    }
}
