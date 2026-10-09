package unit.parser.initializations;

import org.junit.jupiter.api.Test;
import parser.initializations.Initialization;
import parser.initializations.leafs.VariableInitialization;
import parser.initializations.nodes.RepeatedInitialization;
import parser.initializations.nodes.RepeatedInitialization.Interval;
import parser.initializations.nodes.StructInitialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RepeatedInitialization}.
 * <p>
 * Follows Vladimir Khorikov's testing conventions (UnitOfWork_StateUnderTest_ExpectedBehavior).
 * </p>
 */
class RepeatedInitializationTest {
    @Test
    void Put_WithEmptyPath_ThrowsRuntimeException() {
        RepeatedInitialization repeated = new RepeatedInitialization(10, Collections.emptyList());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repeated.put("", new VariableInitialization("5"))
        );
        assertEquals("RepeatedInitialization: cannot insert an element in the root", exception.getMessage());
    }

    @Test
    void Put_WithInvalidIndexFormat_ThrowsRuntimeException() {
        RepeatedInitialization repeated = new RepeatedInitialization(10, Collections.emptyList());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repeated.put("abc", new VariableInitialization("5"))
        );
        assertEquals("RepeatedInitialization: index is not a valid number: abc", exception.getMessage());
    }

    @Test
    void Put_WithIndexOutOfBounds_ThrowsRuntimeException() {
        RepeatedInitialization repeated = new RepeatedInitialization(5, Collections.emptyList());

        RuntimeException exNegative = assertThrows(
                RuntimeException.class,
                () -> repeated.put("-1", new VariableInitialization("5"))
        );
        assertEquals("RepeatedInitialization: index out of bounds: -1", exNegative.getMessage());
        RuntimeException exOverflow = assertThrows(
                RuntimeException.class,
                () -> repeated.put("5", new VariableInitialization("5"))
        );
        assertEquals("RepeatedInitialization: index out of bounds: 5", exOverflow.getMessage());
    }

    @Test
    void Put_ReplacesElementAndReturnsPreviousValue_InSingleElementInterval() {
        VariableInitialization initialValue = new VariableInitialization("A");
        RepeatedInitialization.Interval interval = new RepeatedInitialization.Interval(0, 0, initialValue);
        RepeatedInitialization repeated = new RepeatedInitialization(1, Collections.singletonList(interval));

        VariableInitialization newValue = new VariableInitialization("B");
        Initialization previous = repeated.put("0", newValue);

        assertEquals(initialValue, previous);
        assertEquals(newValue, repeated.find("0").orElse(null));
    }

    @Test
    void Put_SplitsIntervalWhenMutatingSingleIndexInMiddle() {
        VariableInitialization defaultValue = new VariableInitialization("A");
        RepeatedInitialization.Interval interval = new RepeatedInitialization.Interval(0, 9, defaultValue);
        RepeatedInitialization repeated = new RepeatedInitialization(10, Collections.singletonList(interval));

        VariableInitialization newValue = new VariableInitialization("B");
        repeated.put("3", newValue);

        assertEquals(defaultValue, repeated.find("2").orElse(null));
        assertEquals(defaultValue, repeated.find("4").orElse(null));
        assertEquals(newValue, repeated.find("3").orElse(null));
        assertEquals("[3(A), B, 6(A)]", repeated.variableValue());
    }

    @Test
    void Put_SplitsIntervalAtStartAndEnd() {
        VariableInitialization defaultValue = new VariableInitialization("X");
        RepeatedInitialization.Interval interval = new RepeatedInitialization.Interval(0, 4, defaultValue);
        RepeatedInitialization repeated = new RepeatedInitialization(5, Collections.singletonList(interval));

        repeated.put("0", new VariableInitialization("FIRST"));
        repeated.put("4", new VariableInitialization("LAST"));

        assertEquals("FIRST", repeated.find("0").orElseThrow(AssertionError::new).variableValue());
        assertEquals("X", repeated.find("1").orElseThrow(AssertionError::new).variableValue());
        assertEquals("X", repeated.find("3").orElseThrow(AssertionError::new).variableValue());
        assertEquals("LAST", repeated.find("4").orElseThrow(AssertionError::new).variableValue());
    }

    @Test
    void Put_WithNestedPath_SplitsAndMutatesNestedElementWithoutAffectingOthers() {
        StructInitialization point = new StructInitialization();
        point.put("X", new VariableInitialization("1"));
        point.put("Y", new VariableInitialization("2"));
        RepeatedInitialization.Interval interval = new RepeatedInitialization.Interval(0, 2, point);
        RepeatedInitialization repeated = new RepeatedInitialization(3, Collections.singletonList(interval));

        repeated.put("1#X", new VariableInitialization("99"));

        assertEquals(new VariableInitialization("1"), repeated.find("0#X").orElse(null));
        assertEquals(new VariableInitialization("1"), repeated.find("2#X").orElse(null));
        assertEquals(new VariableInitialization("99"), repeated.find("1#X").orElse(null));
    }

    @Test
    void Put_WithNestedPathOnNullElement_ThrowsRuntimeException() {
        RepeatedInitialization.Interval interval = new RepeatedInitialization.Interval(0, 4, null);
        RepeatedInitialization repeated = new RepeatedInitialization(5, Collections.singletonList(interval));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repeated.put("2#X", new VariableInitialization("9"))
        );

        assertEquals("RepeatedInitialization: element at index 2 is null", exception.getMessage());
    }
    
    @Test
    void Find_WithEmptyPath_ReturnsTheArrayItself() {
        RepeatedInitialization repeated = new RepeatedInitialization(10,
                Collections.singletonList(new Interval(0, 10, new VariableInitialization("A")))
        );

        assertSame(repeated, repeated.find("").orElse(null));
    }

    @Test
    void Find_ForValidIndex_ReturnsTheElementOfItsInterval() {
        List<Interval> intervals = new ArrayList<>();
        VariableInitialization vB = new VariableInitialization("B");
        intervals.add(new Interval(0, 2, new VariableInitialization("A")));
        intervals.add(new Interval(3, 5, vB));
        intervals.add(new Interval(6, 10, new VariableInitialization("A")));
        RepeatedInitialization repeated = new RepeatedInitialization(10, intervals);

        assertSame(vB, repeated.find("4").orElse(null));
        assertEquals("A", repeated.find("0").orElseThrow(AssertionError::new).variableValue());
        assertEquals("A", repeated.find("9").orElseThrow(AssertionError::new).variableValue());
    }

    @Test
    void Find_ForIndexOutOfRangeOrNotANumber_IsEmpty() {
        RepeatedInitialization repeated = new RepeatedInitialization(10,
                Collections.singletonList(new Interval(0, 9, new VariableInitialization("A")))
        );

        assertFalse(repeated.find("-1").isPresent());
        assertFalse(repeated.find("10").isPresent());
        assertFalse(repeated.find("x").isPresent());
    }

    @Test
    void Find_WhenIntervalHasNoInitialization_IsEmpty() {
        RepeatedInitialization repeated = new RepeatedInitialization(10,
                Collections.singletonList(new Interval(0, 9, null))
        );
        assertFalse(repeated.find("2").isPresent());
    }

    @Test
    void Find_WithPathPastTheIndex_DelegatesToTheElement() {
        StructInitialization point = new StructInitialization();
        point.put("X", new VariableInitialization("7"));
        RepeatedInitialization repeated = new RepeatedInitialization(10,
                Collections.singletonList(new Interval(0, 2, point))
        );

        assertEquals(new VariableInitialization("7"), repeated.find("2#X").orElse(null));
        assertFalse(repeated.find("2#Y").isPresent());
    }
}