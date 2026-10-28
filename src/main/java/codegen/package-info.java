/**
 * Code generation: Vivado HLS C++ of a fuzzy controller.
 * <p>
 * Port of the code generator of Luca Sarramone's FLC generator (Generador-FLC, commit {@code c304c1d}), used
 * with his permission. {@link codegen.ControllerModel} is the input (it replaces the static {@code IOVars} and
 * {@code Compiler.table} of the original) and {@link codegen.CodeGenerator} returns the three files as text.
 * Each stage emits its own code: {@link codegen.Fuzzifier}, {@link codegen.evaluation.EvaluationMethod} and
 * {@link codegen.defuzzification.Defuzzifier}; each fuzzy set emits its membership function
 * ({@link codegen.fuzzysets.FuzzySet}).
 * </p>
 * <p>
 * Every value is an unsigned integer of N bits ({@code ap_uint<N>}); degrees go from 0 to {@code 2^N - 1}.
 * The expected output is fixed by the reference files of {@code src/test/resources/golden/luca}.
 * </p>
 *
 * @since 1.0
 */
package codegen;
