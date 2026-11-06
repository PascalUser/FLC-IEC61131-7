package api;

import compiler.FlcCompiler;
import compiler.GeneratedFile;

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
public final class Application {
    /** The code was generated (there may be warnings). */
    public static final int CODE_OK = 0;
    /** The program has errors or uses something the generator does not support; nothing was written. */
    public static final int CODE_COMPILER_ERR = 1;
    /** Wrong arguments, or a file could not be read or written. */
    public static final int CODE_ARGUMENT_ERR = 2;
    /** Unexpected failure of the compiler itself. */
    public static final int CODE_INTERNAL_ERR = 3;

    private Application() {
        // Utility class - not instantiable
    }

    /**
     * Runs the compiler.
     *
     * @param args command-line arguments: the program and, optionally, the output directory
     * @param out  stream for progress messages
     * @param err  stream for diagnostics and errors
     * @return the exit status: {@link #CODE_OK}, {@link #CODE_COMPILER_ERR}, {@link #CODE_ARGUMENT_ERR} or
     *         {@link #CODE_INTERNAL_ERR}
     */
    public static int run(final String[] args, final PrintStream out, final PrintStream err) {
        if (args.length < 1 || args.length > 2) {
            err.println(ERR_INVALID_ARGUMENTS());
            return CODE_ARGUMENT_ERR;
        }
        Path program = Paths.get(args[0]).toAbsolutePath();
        Path outputDirectory = (args.length == 2) ? Paths.get(args[1]) : program.getParent();

        FlcCompiler compiler;
        List<GeneratedFile> files;
        try (Reader sourceCode = Files.newBufferedReader(program, StandardCharsets.UTF_8)) {
            String sourceName = getProgramName(program);
            compiler = new FlcCompiler(sourceName, sourceCode);
            try {
                files = compiler.compile();
            } catch (RuntimeException e) {
                err.println("Internal compiler error, please report it with the program: " + e);
                e.printStackTrace(err);
                return CODE_INTERNAL_ERR;
            }
        } catch (IOException e) {
            err.println("Cannot read " + program + ": " + e.getMessage());
            return CODE_ARGUMENT_ERR;
        }
        err.println(compiler.log());
        if (files.isEmpty()) {
            err.println("No code generated: " + program.getFileName() + " has errors");
            return CODE_COMPILER_ERR;
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
            return CODE_ARGUMENT_ERR;
        }
        return CODE_OK;
    }

    /** Name of the program without extension and with spaces replaced by {@code _}. */
    private static String getProgramName(final Path program) {
        String name = String.valueOf(program.getFileName());
        int dot = name.lastIndexOf('.');
        return ((dot > 0) ? name.substring(0, dot) : name).replace(' ', '_');
    }

    private static String ERR_INVALID_ARGUMENTS() {
        return String.join(System.lineSeparator(),
                "Usage: java -jar flc-iec61131-7-compiler.jar <program.fcl> [output directory]",
                "Compiles an IEC 61131-7 FCL program into Vivado HLS C++:",
                "<base>_out.cpp, <base>_out.h and <base>_outTB.cpp (by default next to the program)."
        );
    }
}
