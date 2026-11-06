
/**
 * Factory and node types for building and manipulating IEC 61131-7
 * variable initializations.
 * <p>
 * This package provides the {@link compiler.parser.initializations.Initialization} interface hierarchy along with
 * {@link compiler.parser.initializations.Factory} for creating default initializations, and concrete implementations
 * for leaf values ({@link compiler.parser.initializations.leafs.VariableInitialization},
 * {@link compiler.parser.initializations.leafs.EnumeratedInitialization},
 * {@link compiler.parser.initializations.leafs.DefaultInitialization},
 * {@link compiler.parser.initializations.leafs.SubrangeInitialization}) and
 * composite nodes ({@link compiler.parser.initializations.nodes.StructInitialization},
 * {@link compiler.parser.initializations.nodes.RepeatedInitialization}).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
package compiler.parser.initializations;