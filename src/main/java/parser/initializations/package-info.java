
/**
 * Factory and node types for building and manipulating IEC 61131-7
 * variable initializations.
 * <p>
 * This package provides the {@link Initialization} interface hierarchy along with
 * {@link Factory} for creating default initializations, and concrete implementations
 * for leaf values ({@link leafs.VariableInitialization}, {@link leafs.EnumeratedInitialization},
 * {@link leafs.DefaultInitialization}, {@link leafs.SubrangeInitialization}) and
 * composite nodes ({@link nodes.StructInitialization}, {@link nodes.RepeatedInitialization}).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
package parser.initializations;