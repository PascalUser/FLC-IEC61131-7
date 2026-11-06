package unit.generator.fuzzysets;

import compiler.generator.CppWriter;
import compiler.generator.fuzzysets.TrapezoidFuzzySet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrapezoidFuzzySetTest {

    private final TrapezoidFuzzySet trapezoid = new TrapezoidFuzzySet("middle", 30, 50, 70, 80);

    @Test
    void CompileFuzzSet_WithDefaultParameters_GeneratesCorrectConditionalLogic() {
        CppWriter out = new CppWriter();
        String expectedCpp = String.join("",
                "\tif (x < 30 || x > 80) \n",
                "\t\txFuzz[0] = 0;\n",
                "\telse \n",
                "\t\tif (x < 50) \n",
                "\t\t\txFuzz[0] = functionx01 * (x-30); \n",
                "\t\telse \n",
                "\t\t\tif (x > 70) \n",
                "\t\t\t\txFuzz[0] = functionx02 * (80 - x);\n",
                "\t\t\telse \n",
                "\t\t\t\txFuzz[0] = 255;\n");

        trapezoid.compileFuzzSet(out, 255, "x", 0);
        String actualCpp = out.toString();

        assertEquals(expectedCpp, actualCpp);
    }

    @Test
    void CompileFunctionSlope_WithDefaultParameters_GeneratesCorrectConstantDeclarations() {
        CppWriter out = new CppWriter();
        String expectedCpp = "const fixed_int functionx01 = 255/(50 - 30); \n"
                + "const fixed_int functionx02 = 255/(80 - 70); \n";

        trapezoid.compileFunctionSlope(out, 255, "functionx0");
        String actualCpp = out.toString();

        assertEquals(expectedCpp, actualCpp);
    }
}