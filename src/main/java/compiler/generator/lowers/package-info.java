/**
 * Lowering: from what the parser produced (symbol table, rule block AST, order of the inputs and outputs) to
 * the {@link compiler.generator.ControllerModel} of the code generator.
 * <p>
 * {@link compiler.generator.lowers.ControllerLower} is the entry point and also the check of what the generator supports:
 * anything it cannot produce is reported as {@link compiler.repository.diagnostics.UnsupportedFeature}. The resolution of
 * symbol names lives only in {@link compiler.generator.lowers.utils.SymbolKeys}.
 * </p>
 *
 * @since 1.0
 */
package compiler.generator.lowers;
