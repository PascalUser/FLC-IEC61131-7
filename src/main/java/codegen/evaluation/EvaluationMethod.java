package codegen.evaluation;

import codegen.ControllerModel;
import codegen.CppWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rule evaluation stage: owns the rule matrix and emits the {@code rulesEvaluation} function.
 * <p>
 * The rule matrix has one row per rule, in source order, and one column per variable with the layout of
 * {@link ControllerModel} (inputs, then outputs); each cell is the index of a fuzzy set of that variable. Port of
 * {@code code_generation.evaluation_method.EvalMethod} (Luca Sarramone, Generador-FLC {@code c304c1d}).
 * </p>
 *
 * @author Luca Sarramone (original generator)
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class EvaluationMethod {
    private final List<int[]> rules;

    /**
     * Creates the evaluation of a rule matrix.
     *
     * @param rules one row per rule; every row has one cell per input and output
     */
    protected EvaluationMethod(final List<int[]> rules) {
        List<int[]> copy = new ArrayList<>();
        for (int[] rule : rules) {
            copy.add(rule.clone());
        }
        this.rules = Collections.unmodifiableList(copy);
    }

    /**
     * Returns the number of rules.
     *
     * @return the number of rows of the matrix
     */
    public final int rows() {
        return rules.size();
    }

    /**
     * Returns a cell of the rule matrix.
     *
     * @param row    rule index
     * @param column variable column (see {@link ControllerModel#inputColumn} and
     *               {@link ControllerModel#outputColumn})
     * @return the index of the fuzzy set of that variable in that rule
     */
    public final int set(final int row, final int column) {
        return rules.get(row)[column];
    }

    /**
     * Emits the {@code rulesEvaluation} function.
     *
     * @param out   destination of the code
     * @param model the controller
     */
    public abstract void compileEvalMethod(CppWriter out, ControllerModel model);

    /**
     * Emits the declaration of {@code rulesEvaluation} for the header.
     *
     * @param out destination of the code
     */
    public abstract void compileHeader(CppWriter out);
}
