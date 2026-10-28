package unit.codegen.fuzzysets;

import codegen.CppWriter;
import codegen.fuzzysets.TrapezoidFuzzySet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The reference examples have no trapezoid, so its code is fixed here. It is the corrected version of Luca's
 * {@code TrapezoidFS}: 0 outside {@code [a, d]}, rising slope up to {@code b}, maximum degree on the plateau,
 * falling slope after {@code c}. Executed for every input of 8 bits, it matches the trapezoid with integer slopes.
 */
class TrapezoidFuzzySetTest {

    private final TrapezoidFuzzySet trapezoid = new TrapezoidFuzzySet("middle", 30, 50, 70, 80);

    @Test
    void Membership_is_zero_outside_rising_falling_and_maximum_on_the_plateau() {
        CppWriter out = new CppWriter();

        trapezoid.compileFuzzSet(out, 255, "x", 0);

        assertEquals(String.join("",
                "\tif (x < 30 || x > 80) \n",
                "\t\txFuzz[0] = 0;\n",
                "\telse \n",
                "\t\tif (x < 50) \n",
                "\t\t\txFuzz[0] = functionx01 * (x-30); \n",
                "\t\telse \n",
                "\t\t\tif (x > 70) \n",
                "\t\t\t\txFuzz[0] = functionx02 * (80 - x);\n",
                "\t\t\telse \n",
                "\t\t\t\txFuzz[0] = 255;\n"), out.toString());
    }

    @Test
    void Slopes_are_the_maximum_degree_over_the_width_of_each_side() {
        CppWriter out = new CppWriter();

        trapezoid.compileFunctionSlope(out, 255, "functionx0");

        assertEquals("const fixed_int functionx01 = 255/(50 - 30); \n"
                + "const fixed_int functionx02 = 255/(80 - 70); \n", out.toString());
    }
}
