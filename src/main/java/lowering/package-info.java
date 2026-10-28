/**
 * Lowering: from what the parser produced (symbol table, rule block AST, order of the inputs and outputs) to
 * the {@link codegen.ControllerModel} of the code generator.
 * <p>
 * {@link lowering.ControllerLowering} is the entry point and also the check of what the generator supports:
 * anything it cannot produce is reported as {@link utils.diagnostics.UnsupportedFeature}. The resolution of
 * symbol names lives only in {@link lowering.SymbolKeys}.
 * </p>
 *
 * @since 1.0
 */
package lowering;
