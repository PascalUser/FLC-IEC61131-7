package system.simulation;

import compiler.ast.AbstractSyntaxTree;
import compiler.generator.ControllerModel;
import compiler.generator.lowers.ControllerLower;
import compiler.repository.DiagnosticsHandler;
import compiler.repository.SymbolTable;
import org.junit.jupiter.api.Test;
import utils.simulation.OutputComparison;
import utils.simulation.QuantizedSimulator;
import utils.simulation.ReferenceSimulator;
import utils.simulation.SimulationComparison;
import utils.*;
import utils.supports.ParserTestSupport;

import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Simulation of the reference controllers (written in FCL): the generated hardware against the ideal controller.
 * The expected values of single inputs are computed by hand from the terms and rules of the programs.
 */
public class ControllerSimulationIT extends ParserTestSupport {

    private ControllerModel model(String example) throws Exception {
        ParsingResults results;
        try (Reader source = TestResources.reader("golden/luca/" + example + "/" + example + ".fcl")) {
            results = parse(source);
        }
        SymbolTable symbolTable = results.symbolTable();
        AbstractSyntaxTree root = results.abstractSyntaxTree();
        DiagnosticsHandler diagnostics = new DiagnosticsHandler();
        Optional<ControllerModel> modelObj = ControllerLower.newInstance(symbolTable, root, diagnostics).adapt();
        assertTrue(modelObj.isPresent(), "model was not created");
        return modelObj.get();
    }

    /**
     * angulo = velocidad = 127 only activates "z" of both (degree 252 = 36 * 7 and 12 * 21 in the hardware, 1
     * ideally), so the rule z * z = z gives the singleton z = 127.
     */
    @Test
    void Pendulum_at_rest_pushes_with_the_center_force() throws Exception {
        ControllerModel pendulum = model("pendulo_invertido");

        assertEquals(OptionalInt.of(127), new QuantizedSimulator(pendulum).evaluate(127, 127)[0]);
        assertEquals(127.0, new ReferenceSimulator(pendulum).evaluate(127, 127)[0], 1e-9);
    }

    /**
     * angulo = 120 is "ns" with degree 175 = 25 * 7 (0.7 ideally) and velocidad = 127 is "z", so only the rule
     * ns * z = ps fires and the force is the singleton ps = 149.
     */
    @Test
    void Single_active_rule_gives_its_singleton() throws Exception {
        ControllerModel pendulum = model("pendulo_invertido");

        assertEquals(OptionalInt.of(149), new QuantizedSimulator(pendulum).evaluate(120, 127)[0]);
        assertEquals(149.0, new ReferenceSimulator(pendulum).evaluate(120, 127)[0], 1e-9);
    }

    /**
     * anguloCamion = 0 is the left foot of its first triangle (0, 31, 70): every term has degree 0, no rule fires
     * and the generated centroid divides by zero. It is what DEFAULT is for, which the generator does not have.
     */
    @Test
    void Truck_at_the_extreme_angle_fires_no_rule() throws Exception {
        ControllerModel truck = model("EstacionamientoCamion");

        assertFalse(new QuantizedSimulator(truck).evaluate(127, 0)[0].isPresent());
        assertTrue(Double.isNaN(new ReferenceSimulator(truck).evaluate(127, 0)[0]));
    }

    /**
     * Over every input, the generated pendulum stays within 5 units of 255 of the ideal one (4.66 when this test
     * was written). It fails if a change makes the generated controller less accurate.
     */
    @Test
    void Generated_pendulum_is_close_to_the_ideal_one_for_every_input() throws Exception {
        List<OutputComparison> comparison = SimulationComparison.compare(model("pendulo_invertido"), 1);

        OutputComparison force = comparison.get(0);
        assertEquals(256 * 256, force.evaluated());
        assertEquals(0, force.undefinedInHardware());
        assertTrue(force.maxError() <= 5, force.toString());
    }
}
