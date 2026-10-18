
/**
 * Factory and node types for building and manipulating IEC 61131-7
 * variable initializations.
 * <p>
 * This package provides the {@link parser.initializations.Initialization} interface hierarchy along with
 * {@link parser.initializations.Factory} for creating default initializations, and concrete implementations
 * for leaf values ({@link parser.initializations.leafs.VariableInitialization},
 * {@link parser.initializations.leafs.EnumeratedInitialization},
 * {@link parser.initializations.leafs.DefaultInitialization},
 * {@link parser.initializations.leafs.SubrangeInitialization}) and
 * composite nodes ({@link parser.initializations.nodes.StructInitialization},
 * {@link parser.initializations.nodes.RepeatedInitialization}).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
package parser.initializations;