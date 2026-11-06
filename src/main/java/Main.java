import api.Application;

/**
 * Main entry point for the IEC 61131-7 compiler: {@code java -jar flc-iec61131-7-compiler.jar <program.fcl>
 * [output directory]}. See {@link Application}.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
class Main {
    /**
     * Runs the compiler as a command-line program.
     *
     * @param args source file and optional output directory
     */
    public static void main(final String[] args) {
        System.exit(Application.run(args, System.out, System.err));
    }
}
