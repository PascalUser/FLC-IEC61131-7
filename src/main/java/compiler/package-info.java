/**
 * Compiler pipeline facade.
 * <p>
 * {@link compiler.FlcCompiler} chains the phases (lexer, parser, lowering, code generation) and returns the
 * generated files as {@link compiler.GeneratedFile} values, without touching the file system.
 * </p>
 *
 * @since 1.0
 */
package compiler;
