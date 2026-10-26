package unit.ast.ruleblock;

import ast.ruleblock.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightTest {

    @Test
    void Numeric_StoresNumericKind() {
        Weight weight = Weight.numeric("0.5");

        assertEquals("0.5", weight.value());
        assertEquals(Weight.Kind.NUMERIC, weight.kind());
    }

    @Test
    void Identifier_StoresIdentifierKind() {
        Weight weight = Weight.identifier("WEIGHT");

        assertEquals("WEIGHT", weight.value());
        assertEquals(Weight.Kind.IDENTIFIER, weight.kind());
    }

    @Test
    void EqualsAndHashCode_AreBasedOnKindAndValue() {
        Weight first = Weight.numeric("0.5");
        Weight second = Weight.numeric("0.5");

        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, Weight.identifier("0.5"));
        assertNotEquals(null, first);
    }

    @Test
    void ToString_ContainsKindAndValue() {
        String result = Weight.identifier("WEIGHT").toString();

        assertTrue(result.contains("IDENTIFIER"));
        assertTrue(result.contains("WEIGHT"));
    }

    @Test
    void Constructor_WithEmptyValue_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Weight.numeric(""));
        assertThrows(IllegalArgumentException.class, () -> Weight.identifier(""));
    }

    @Test
    void Constructor_WithNullValue_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Weight.numeric(null));
        assertThrows(NullPointerException.class, () -> Weight.identifier(null));
    }
}
