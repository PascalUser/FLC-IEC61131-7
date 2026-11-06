package unit.generator.fuzzysets;

import compiler.generator.fuzzysets.FuzzySet;
import compiler.generator.fuzzysets.SShapeFuzzySet;
import compiler.generator.fuzzysets.SingletonFuzzySet;
import compiler.generator.fuzzysets.TrapezoidFuzzySet;
import compiler.generator.fuzzysets.TriangularFuzzySet;
import compiler.generator.fuzzysets.ZShapeFuzzySet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuzzySetTest {
    private static final int RANGE = 255;

    private static FuzzySet createFuzzySet(String shape) {
        switch (shape) {
            case "Z": return new ZShapeFuzzySet("z", 54, 78);
            case "S": return new SShapeFuzzySet("s", 166, 177);
            case "TRIANGLE": return new TriangularFuzzySet("t", 0, 99, 113);
            case "TRAPEZOID": return new TrapezoidFuzzySet("p", 30, 50, 70, 80);
            default: return new SingletonFuzzySet("v", 5);
        }
    }

    @ParameterizedTest(name = "{0}({1}) hardware degree = {2}")
    @CsvSource({
            "Z,         0,   255",
            "Z,         54,  240",
            "Z,         66,  120",
            "Z,         78,  0",
            "Z,         200, 0",
            "S,         100, 0",
            "S,         170, 92",
            "S,         177, 253",
            "S,         178, 255",
            "TRIANGLE,  0,   0",
            "TRIANGLE,  50,  100",
            "TRIANGLE,  99,  198",
            "TRIANGLE,  106, 126",
            "TRIANGLE,  113, 0",
            "TRIANGLE,  200, 0",
            "TRAPEZOID, 20,  0",
            "TRAPEZOID, 40,  120",
            "TRAPEZOID, 60,  255",
            "TRAPEZOID, 75,  125",
            "TRAPEZOID, 90,  0",
            "SINGLETON, 5,   255",
            "SINGLETON, 6,   0",
    })
    void QuantizedDegree_GivenVariousShapesAndInputs_ReturnsExpectedQuantizedDegree(
            String shape, int x, int expectedQuantized
    ) {
        FuzzySet set = createFuzzySet(shape);
        int actualQuantized = set.quantizedDegree(x, RANGE);
        assertEquals(expectedQuantized, actualQuantized, "Generated code quantized degree mismatch");
    }

    @ParameterizedTest(name = "{0}({1}) ideal degree = {2}")
    @CsvSource({
            "Z,         0,   1.0",
            "Z,         54,  1.0",
            "Z,         66,  0.5",
            "Z,         78,  0.0",
            "Z,         200, 0.0",
            "S,         100, 0.0",
            "S,         170, 0.36363636363636365",
            "S,         177, 1.0",
            "S,         178, 1.0",
            "TRIANGLE,  0,   0.0",
            "TRIANGLE,  50,  0.5050505050505051",
            "TRIANGLE,  99,  1.0",
            "TRIANGLE,  106, 0.5",
            "TRIANGLE,  113, 0.0",
            "TRIANGLE,  200, 0.0",
            "TRAPEZOID, 20,  0.0",
            "TRAPEZOID, 40,  0.5",
            "TRAPEZOID, 60,  1.0",
            "TRAPEZOID, 75,  0.5",
            "TRAPEZOID, 90,  0.0",
            "SINGLETON, 5,   1.0",
            "SINGLETON, 6,   0.0",
    })
    void Degree_GivenVariousShapesAndInputs_ReturnsExpectedIdealDegree(String shape, int x, double expectedIdeal) {
        FuzzySet set = createFuzzySet(shape);
        double actualIdeal = set.degree(x);
        assertEquals(expectedIdeal, actualIdeal, 1e-12, "Ideal mathematical degree mismatch");
    }
}