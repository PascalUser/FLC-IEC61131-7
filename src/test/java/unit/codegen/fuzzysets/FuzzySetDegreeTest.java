package unit.codegen.fuzzysets;

import codegen.fuzzysets.FuzzySet;
import codegen.fuzzysets.SShapeFuzzySet;
import codegen.fuzzysets.SingletonFuzzySet;
import codegen.fuzzysets.TrapezoidFuzzySet;
import codegen.fuzzysets.TriangularFuzzySet;
import codegen.fuzzysets.ZShapeFuzzySet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Degrees of each fuzzy set with 8 bits: the one the generated code computes (integer slope
 * {@code 255 / width}, so the maximum is not always reached) and the ideal one of IEC 61131-7.
 */
class FuzzySetDegreeTest {
    private static final int RANGE = 255;

    private static FuzzySet set(String shape) {
        switch (shape) {
            case "Z":
                return new ZShapeFuzzySet("z", 54, 78);
            case "S":
                return new SShapeFuzzySet("s", 166, 177);
            case "TRIANGLE":
                return new TriangularFuzzySet("t", 0, 99, 113);
            case "TRAPEZOID":
                return new TrapezoidFuzzySet("p", 30, 50, 70, 80);
            default:
                return new SingletonFuzzySet("v", 5);
        }
    }

    @ParameterizedTest(name = "{0}({1}) = {2} / {3}")
    @CsvSource({
            // Z(54, 78): slope 255 / 24 = 10
            "Z,         0,   255, 1",
            "Z,         54,  240, 1",
            "Z,         66,  120, 0.5",
            "Z,         78,  0,   0",
            "Z,         200, 0,   0",
            // S(166, 177): slope 255 / 11 = 23
            "S,         100, 0,   0",
            "S,         170, 92,  0.36363636363636365",
            "S,         177, 253, 1",
            "S,         178, 255, 1",
            // triangle (0, 99, 113): slopes 255 / 99 = 2 and 255 / 14 = 18
            "TRIANGLE,  0,   0,   0",
            "TRIANGLE,  50,  100, 0.5050505050505051",
            "TRIANGLE,  99,  198, 1",
            "TRIANGLE,  106, 126, 0.5",
            "TRIANGLE,  113, 0,   0",
            "TRIANGLE,  200, 0,   0",
            // trapezoid (30, 50, 70, 80): slopes 255 / 20 = 12 and 255 / 10 = 25
            "TRAPEZOID, 20,  0,   0",
            "TRAPEZOID, 40,  120, 0.5",
            "TRAPEZOID, 60,  255, 1",
            "TRAPEZOID, 75,  125, 0.5",
            "TRAPEZOID, 90,  0,   0",
            // singleton 5
            "SINGLETON, 5,   255, 1",
            "SINGLETON, 6,   0,   0",
    })
    void Degree_of_the_generated_code_and_ideal_degree(String shape, int x, int quantized, double ideal) {
        FuzzySet set = set(shape);

        assertEquals(quantized, set.quantizedDegree(x, RANGE), "generated code");
        assertEquals(ideal, set.degree(x), 1e-12, "ideal");
    }
}
