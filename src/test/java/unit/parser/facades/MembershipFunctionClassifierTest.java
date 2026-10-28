package unit.parser.facades;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parser.facades.MembershipFunctionClassifier;
import parser.utils.MembershipFunctionShape;
import utils.enums.Subtype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MembershipFunctionClassifierTest {

    private static List<String> list(String spaceSeparated) {
        return (spaceSeparated == null) ? null : Arrays.asList(spaceSeparated.trim().split("\\s+"));
    }

    private static MembershipFunctionShape classify(String xs, String degrees) {
        return MembershipFunctionClassifier.classify(list(xs), list(degrees));
    }

    @ParameterizedTest(name = "({0}) / ({1}) -> {2} {3}")
    @CsvSource(delimiter = '|', value = {
            // two points: decreasing or increasing ramp
            "30 50          | 1 0         | LINEAR    | 30 50",
            "70 80          | 0 1         | LINEAR    | 70 80",
            // the usual shapes
            "0 5 10         | 0 1 0       | TRIANGLE  | 0 5 10",
            "30 50 70 80    | 0 1 1 0     | TRAPEZOID | 30 50 70 80",
            // shoulders: the extreme points repeat the degree of their neighbour
            "0 5 10         | 1 1 0       | LINEAR    | 5 10",
            "0 5 10         | 0 1 1       | LINEAR    | 0 5",
            "0 5 10         | 1 0 0       | LINEAR    | 0 5",
            "0 5 10 15      | 1 1 0 0     | LINEAR    | 5 10",
            // interior points on a flat segment
            "0 5 7 8 10     | 0 1 1 1 0   | TRAPEZOID | 0 5 8 10",
            // real literals as normalized by the lexer (0.0 is stored as .0)
            "17.5 20.0 22.5 | .0 1.0 .0   | TRIANGLE  | 17.5 20.0 22.5",
            "1.5E1 2.0e1    | 1 0         | LINEAR    | 1.5E1 2.0e1",
            // singleton
            "5              | 1           | SINGLETON | 5",
            "-5             | 1           | SINGLETON | -5",
    })
    void Shape_is_recognized_from_points_and_degrees(String xs, String degrees, Subtype subtype, String params) {
        assertEquals(new MembershipFunctionShape(subtype, list(params)), classify(xs, degrees));
    }

    @ParameterizedTest(name = "({0}) / ({1}): {2}")
    @CsvSource(delimiter = '|', value = {
            "0 5 10         | 1 0 1       | inverted triangle (V)",
            "0 5 10 15 20   | 0 1 0 1 0   | two peaks (W)",
            "30 50          | 1 1         | constant",
            "30 50          | 0 0         | constant zero",
            "5              | 0           | single point with degree 0",
            "30 50          | 0 .5        | degree between 0 and 1 (Open Level)",
            "50 30          | 0 1         | x in descending order",
            "30 30          | 0 1         | repeated x",
    })
    void Anything_else_is_complex(String xs, String degrees, String reason) {
        assertEquals(new MembershipFunctionShape(Subtype.COMPLEX, null), classify(xs, degrees), reason);
    }

    @Test
    void Point_given_by_a_variable_is_variable() {
        assertEquals(new MembershipFunctionShape(Subtype.VARIABLE, null), classify("P1 50", "0 1"));
    }

    @Test
    void Different_number_of_x_and_degrees_is_a_programming_error() {
        assertThrows(IllegalArgumentException.class, () -> classify("0 5", "1"));
        assertThrows(IllegalArgumentException.class,
                () -> MembershipFunctionClassifier.classify(Collections.emptyList(), Collections.emptyList()));
    }
}
