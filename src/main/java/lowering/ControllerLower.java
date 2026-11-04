package lowering;

import codegen.ControllerModel;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lowering.builders.ControllerModelBuilder;
import lowering.semantics.BitWidthAnalyzer;
import lowering.semantics.FuzzyVariablesAnalyzer;
import lowering.semantics.RuleAnalyzer;
import lowering.utils.ControllerModelState;
import lowering.contexts.LoweringContext;
import utils.DiagnosticsHandler;

import java.util.Optional;

/**
 * Builds the {@link ControllerModel} of the code generator from what the parser produced: the symbol table (the
 * named elements: variables, FUZZIFY/DEFUZZIFY blocks, terms, pragmas), the rule block AST and the order of the
 * inputs and outputs.
 * <p>
 * It is also the check of what the generator supports. The generated controller works with unsigned integers of
 * N bits, so:
 * </p>
 * <ul>
 *   <li>N comes from {@code PRAGMA bit_width N;} in an OPTION block (1..30), or is 8 with a warning;</li>
 *   <li>inputs and outputs must be of an integer type (REAL is not supported) and every point an integer
 *       in {@code 0 .. 2^N - 1};</li>
 *   <li>each input needs a FUZZIFY block and each output a DEFUZZIFY block with {@code METHOD: CoGS} and only
 *       singleton terms; {@code DEFAULT} is ignored with a warning (the generator has no default value);</li>
 *   <li>the rules must follow {@link RuleLowering}.</li>
 * </ul>
 * <p>
 * Every problem is reported to the {@link DiagnosticsHandler}; the model is built only if there was no error.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class ControllerLower {
    private final LoweringContext ctx;

    /**
     * Creates the lowering of a function block.
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "The symbol table is shared by every phase by design; the lowering only reads it"
    )
    public ControllerLower(final LoweringContext initContext) {
        this.ctx = initContext;
    }

    /**
     * Builds the controller model.
     * <p>
     * Inputs and outputs are taken in declaration order (the {@code parameters} of the function block entry):
     * it fixes the signature of the controller and the bits of each output in its result.
     *
     * @return the model, or empty if the program has something the generator does not support (reported)
     */
    public Optional<ControllerModel> adapt() {
        ControllerModelBuilder controller = new ControllerModelBuilder();

        BitWidthAnalyzer bitWidthAnalyzer = new BitWidthAnalyzer(ctx);
        if (!bitWidthAnalyzer.construct(controller)) {
            return Optional.empty();
        }

        FuzzyVariablesAnalyzer fuzzyVariablesAnalyzer = new FuzzyVariablesAnalyzer(ctx, bitWidthAnalyzer);
        if (!fuzzyVariablesAnalyzer.construct(controller)) {
            return Optional.empty();
        }

        RuleAnalyzer ruleAnalyzer = new RuleAnalyzer(ctx);
        if (!ruleAnalyzer.construct(controller)) {
            return Optional.empty();
        }

        return Optional.of(new ControllerModel(
                new ControllerModelState(
                        controller.bitWidth(),
                        controller.inputs(),
                        controller.outputs(),
                        controller.evaluationMethod(),
                        controller.defuzzifiers()
                )
        ));
    }
}
