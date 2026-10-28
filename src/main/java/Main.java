import compiler.CommandLine;

/**
 * Main entry point for the IEC 61131-7 compiler: {@code java -jar flc-iec61131-7-compiler.jar <program.fcl>
 * [output directory]}. See {@link CommandLine}.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
class Main {
    /**
     * Starts the compiler and exits with its status (0 when the code was generated).
     *
     * @param args the FCL program and, optionally, the output directory
     */
    public static void main(final String[] args) {
        int status = CommandLine.run(args, System.out, System.err);
        if (status != CommandLine.OK) {
            System.exit(status);
        }
    }
}
