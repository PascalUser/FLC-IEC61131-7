package unit.parser.initializations;

import org.junit.jupiter.api.Test;
import parser.initializations.Initialization;
import parser.initializations.leafs.VariableInitialization;
import parser.initializations.nodes.RepeatedInitialization;
import parser.initializations.nodes.StructInitialization;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RepeatedInitialization}.
 * <p>
 * Follows Vladimir Khorikov's testing conventions (MethodName_StateUnderTest_ExpectedBehavior).
 * Structured using Arrange, Action, Assert concepts.
 * </p>
 */
class RepeatedInitializationTest {
    @Test
    void Put_WithEmptyPath_ThrowsRuntimeException() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, null);

        // Action & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repeated.put("", new VariableInitialization("5"))
        );
        assertEquals("RepeatedInitialization: cannot insert an element in the root", exception.getMessage());
    }

    @Test
    void Put_WithInvalidIndexFormat_ThrowsNumberFormatException() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, null);

        // Action & Assert
        assertThrows(
                NumberFormatException.class,
                () -> repeated.put("abc", new VariableInitialization("5"))
        );
    }

    @Test
    void Put_WithIndexCompletelyOutOfBounds_DoesNothingAndReturnsNull() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(5, new VariableInitialization("A"));

        // Action
        Initialization resultNegative = repeated.put("-5..-1", new VariableInitialization("B"));
        Initialization resultOverflow = repeated.put("5..10", new VariableInitialization("B"));

        // Assert
        assertNull(resultNegative);
        assertNull(resultOverflow);
        assertEquals("[5(A)]", repeated.variableValue()); // El estado interno no debe haber cambiado
    }

    @Test
    void Put_ReplacesElementAndReturnsPreviousValue_InSingleElementInterval() {
        // Arrange
        VariableInitialization initialValue = new VariableInitialization("A");
        RepeatedInitialization repeated = new RepeatedInitialization(1, initialValue);
        VariableInitialization newValue = new VariableInitialization("B");

        // Action
        Initialization previous = repeated.put("0", newValue);

        // Assert
        assertEquals(initialValue, previous);
        assertEquals(newValue, repeated.find("0").orElse(null));
    }

    @Test
    void Put_SplitsIntervalWhenMutatingSingleIndexInMiddle() {
        // Arrange
        VariableInitialization defaultValue = new VariableInitialization("A");
        RepeatedInitialization repeated = new RepeatedInitialization(10, defaultValue);
        VariableInitialization newValue = new VariableInitialization("B");

        // Action
        repeated.put("3", newValue);

        // Assert
        assertEquals(defaultValue, repeated.find("2").orElse(null));
        assertEquals(defaultValue, repeated.find("4").orElse(null));
        assertEquals(newValue, repeated.find("3").orElse(null));
        assertEquals("[3(A), B, 6(A)]", repeated.variableValue());
    }

    @Test
    void Put_SplitsIntervalAtStartAndEnd() {
        // Arrange
        VariableInitialization defaultValue = new VariableInitialization("X");
        RepeatedInitialization repeated = new RepeatedInitialization(5, defaultValue);

        // Action
        repeated.put("0", new VariableInitialization("FIRST"));
        repeated.put("4", new VariableInitialization("LAST"));

        // Assert
        assertEquals("FIRST", repeated.find("0").orElseThrow(AssertionError::new).variableValue());
        assertEquals("X", repeated.find("1").orElseThrow(AssertionError::new).variableValue());
        assertEquals("X", repeated.find("3").orElseThrow(AssertionError::new).variableValue());
        assertEquals("LAST", repeated.find("4").orElseThrow(AssertionError::new).variableValue());
    }

    @Test
    void Put_WithValidRange_UpdatesMultipleElementsAndFusesIntervals() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, new VariableInitialization("A"));

        // Action
        repeated.put("2..5", new VariableInitialization("B"));

        // Assert
        assertEquals("[2(A), 4(B), 4(A)]", repeated.variableValue());
        assertEquals("B", repeated.find("2").orElseThrow(AssertionError::new).variableValue());
        assertEquals("B", repeated.find("5").orElseThrow(AssertionError::new).variableValue());
    }

    @Test
    void Put_WithRangeOutToLeftAndRight_ClampsAndUpdatesValidElements() {
        // Arrange: Probamos los "Edge cases" / Boundary values
        RepeatedInitialization repeated = new RepeatedInitialization(5, new VariableInitialization("A"));

        // Action: un rango que empieza antes de 0 y termina después del máximo
        repeated.put("-2..2", new VariableInitialization("B"));
        repeated.put("4..10", new VariableInitialization("C"));

        // Assert
        assertEquals("[3(B), A, C]", repeated.variableValue());
    }

    @Test
    void Put_WithNestedPath_SplitsAndMutatesNestedElementWithoutAffectingOthers() {
        // Arrange
        StructInitialization point = new StructInitialization();
        point.put("X", new VariableInitialization("1"));
        point.put("Y", new VariableInitialization("2"));
        RepeatedInitialization repeated = new RepeatedInitialization(3, point);

        // Action
        repeated.put("1#X", new VariableInitialization("99"));

        // Assert
        assertEquals(new VariableInitialization("1"), repeated.find("0#X").orElse(null));
        assertEquals(new VariableInitialization("1"), repeated.find("2#X").orElse(null));
        assertEquals(new VariableInitialization("99"), repeated.find("1#X").orElse(null));
    }

    @Test
    void Put_WithNestedPathOnNullElement_ThrowsRuntimeException() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(5, null);

        // Action & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repeated.put("2#X", new VariableInitialization("9"))
        );
        assertEquals("RepeatedInitialization: element is null, cannot delegate X", exception.getMessage());
    }

    @Test
    void Find_WithEmptyPath_ReturnsTheArrayItself() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, new VariableInitialization("A"));

        // Action & Assert
        assertSame(repeated, repeated.find("").orElse(null));
    }

    @Test
    void Find_ForValidIndex_ReturnsTheElementOfItsInterval() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, new VariableInitialization("A"));
        VariableInitialization vB = new VariableInitialization("B");
        repeated.put("3..5", vB);

        // Action & Assert
        assertSame(vB, repeated.find("4").orElse(null));
        assertEquals("A", repeated.find("0").orElseThrow(AssertionError::new).variableValue());
        assertEquals("A", repeated.find("9").orElseThrow(AssertionError::new).variableValue());
    }

    @Test
    void Find_ForIndexOutOfRangeOrNotANumber_IsEmpty() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, new VariableInitialization("A"));

        // Action & Assert
        assertFalse(repeated.find("-1").isPresent());
        assertFalse(repeated.find("10").isPresent());
        assertFalse(repeated.find("x").isPresent());
    }

    @Test
    void Find_WhenIntervalHasNoInitialization_IsEmpty() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, null);

        // Action & Assert
        assertFalse(repeated.find("2").isPresent());
    }

    @Test
    void Find_WithRangeString_ReturnsFirstElementOfRange() {
        // Arrange
        RepeatedInitialization repeated = new RepeatedInitialization(10, new VariableInitialization("A"));
        repeated.put("3..5", new VariableInitialization("B"));

        // Action: buscar pasando un rango como string en vez de un índice suelto
        Optional<Initialization> result = repeated.find("4..8");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("B", result.get().variableValue()); // El índice 4 tiene "B"
    }

    @Test
    void Find_WithPathPastTheIndex_DelegatesToTheElement() {
        // Arrange
        StructInitialization point = new StructInitialization();
        point.put("X", new VariableInitialization("7"));
        RepeatedInitialization repeated = new RepeatedInitialization(10, point);

        // Action & Assert
        assertEquals(new VariableInitialization("7"), repeated.find("2#X").orElse(null));
        assertFalse(repeated.find("2#Y").isPresent());
    }
}
