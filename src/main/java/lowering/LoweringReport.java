package lowering;

import utils.DiagnosticsHandler;
import utils.diagnostics.Error;
import utils.diagnostics.Warning;

/**
 * Collects the diagnostics of the lowering and remembers whether any of them is an error, so that every problem
 * of a program is reported at once and the model is built only if there was none.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
final class LoweringReport {
    private final DiagnosticsHandler diagnostics;
    private boolean failed;

    LoweringReport(final DiagnosticsHandler diagnostics) {
        this.diagnostics = diagnostics;
    }

    void error(final Error error) {
        failed = true;
        diagnostics.add(error);
    }

    void warning(final Warning warning) {
        diagnostics.add(warning);
    }

    boolean failed() {
        return failed;
    }
}
