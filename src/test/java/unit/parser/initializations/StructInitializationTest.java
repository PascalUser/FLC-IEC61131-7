package unit.parser.initializations;

import org.junit.jupiter.api.Test;
import parser.initializations.Initialization;
import parser.initializations.nodes.StructInitialization;
import parser.initializations.leafs.VariableInitialization;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StructInitialization}.
 */
class StructInitializationTest {

    private static StructInitialization colorStruct() {
        StructInitialization rgb = new StructInitialization();
        rgb.put("R", new VariableInitialization("1"));
        rgb.put("G", new VariableInitialization("2"));

        StructInitialization color = new StructInitialization();
        color.put("WHITE", new VariableInitialization("TRUE"));
        color.put("RGB", rgb);
        return color;
    }

    @Test
    void Find_WithEmptyPath_ReturnsNothing() {
        StructInitialization color = colorStruct();
        assertFalse(color.find("").isPresent());
    }

    @Test
    void Find_ForExistingField_ReturnsItsInitialization() {
        assertEquals(new VariableInitialization("TRUE"), colorStruct().find("WHITE").orElse(null));
    }

    @Test
    void Find_ForNestedField_TraversesTheNestedStruct() {
        assertEquals(new VariableInitialization("2"), colorStruct().find("RGB#G").orElse(null));
    }

    @Test
    void Find_ForMissingFieldOrMissingNestedField_IsEmpty() {
        StructInitialization color = colorStruct();

        assertFalse(color.find("BLACK").isPresent());
        assertFalse(color.find("RGB#B").isPresent());
        assertFalse(color.find("BLACK#R").isPresent());
        assertFalse(color.find("WHITE#R").isPresent());
    }

    @Test
    void Find_DoesNotDependOnPreviousCalls() {
        StructInitialization color = colorStruct();

        color.find("RGB#G");
        color.find("WHITE");

        assertEquals(new VariableInitialization("1"), color.find("RGB#R").orElse(null));
        assertFalse(color.find("BLACK").isPresent());
    }

    @Test
    void Put_ReturnsThePreviousValue() {
        StructInitialization color = colorStruct();

        Initialization previous = color.put("WHITE", new VariableInitialization("FALSE"));

        assertEquals(new VariableInitialization("TRUE"), previous);
        assertEquals(new VariableInitialization("FALSE"), color.find("WHITE").orElse(null));
    }

    @Test
    void Put_ForNestedField_OverwritesInsideTheNestedStruct() {
        StructInitialization color = colorStruct();

        color.put("RGB#R", new VariableInitialization("9"));

        assertEquals(new VariableInitialization("9"), color.find("RGB#R").orElse(null));
    }

    @Test
    void Put_WhenIntermediateFieldIsMissing_ThrowsRuntimeException() {
        StructInitialization color = colorStruct();
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> color.put("BLACK#R", new VariableInitialization("9"))
        );
        assertEquals("StructInitialization: first field does not exists", exception.getMessage());
    }

    @Test
    void GetVariableValue_ListsFieldsInInsertionOrder() {
        assertEquals("{WHITE=TRUE, RGB={R=1, G=2}}", colorStruct().variableValue());
        assertEquals("{}", new StructInitialization().variableValue());
    }

    @Test
    void Copy_IsDeepAndIndependentOfTheOriginal() {
        StructInitialization original = colorStruct();
        StructInitialization copy = original.copy();

        copy.put("RGB#R", new VariableInitialization("9"));
        copy.put("WHITE", new VariableInitialization("FALSE"));

        assertEquals(colorStruct(), original);
        assertNotEquals(original, copy);
    }

    @Test
    void Equals_IgnoresFieldInsertionOrder() {
        StructInitialization a = new StructInitialization();
        a.put("X", new VariableInitialization("1"));
        a.put("Y", new VariableInitialization("2"));
        StructInitialization b = new StructInitialization();
        b.put("Y", new VariableInitialization("2"));
        b.put("X", new VariableInitialization("1"));

        assertEquals(a, b);
    }
}