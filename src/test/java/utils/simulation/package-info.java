/**
 * Simulation of the generated controllers, to validate them without Vivado.
 * <p>
 * {@link utils.simulation.QuantizedSimulator} computes what the generated C++ computes (integer arithmetic of
 * {@code ap_uint<N>}); {@link utils.simulation.ReferenceSimulator} computes the ideal controller of IEC 61131-7 in real
 * numbers; {@link utils.simulation.SimulationComparison} measures the difference over all the inputs. Both simulators
 * read the same {@link compiler.generator.ControllerModel} the code generator emits, and the behavior of each fuzzy set lives
 * next to its code in {@link compiler.generator.fuzzysets.FuzzySet}.
 * </p>
 *
 * @since 1.0
 */
package utils.simulation;
