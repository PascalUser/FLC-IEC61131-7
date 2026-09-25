package unit.parser.initializations;

import org.junit.jupiter.api.Test;
import parser.initializations.Initialization;
import parser.initializations.RepeatedInitialization;

import java.util.Arrays;
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

    /**
     * Test stub: always returns a fixed, pre-canned value. It is never
     * verified through interaction, so it is a stub, not a mock.
     */
    private static class StubInitialization implements Initialization {
        private final String value;

        StubInitialization(String value) {
            this.value = value;
        }

        @Override
        public Initialization selectVariable(String variable) {
            return this;
        }

        @Override
        public String getVariableValue() {
            return value;
        }

        @Override
        public Initialization copy() {
            return new StubInitialization(value);
        }
    }

    @Test
    void Constructor_WhenInitialized_SpansFullDimensionWithDefaultValue() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);

        assertEquals(100, repeated.getDimension());
        assertEquals(Collections.singletonList(100), repeated.getRepetitionsList());
        assertNull(repeated.getInitializationsList().get(0));
    }

    @Test
    void AddInterval_WhenMiddleRangeAdded_SplitsIntoThreeSegments() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization value = new StubInitialization("A");

        repeated.addInterval(1, 90, value);

        assertEquals(Arrays.asList(1, 90, 9), repeated.getRepetitionsList());
        List<Initialization> values = repeated.getInitializationsList();
        assertNull(values.get(0));
        assertEquals(value, values.get(1));
        assertNull(values.get(2));
    }

    @Test
    void AddInterval_WhenFirstSegmentOverwritten_LeavesRestUntouched() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization a = new StubInitialization("A");
        StubInitialization b = new StubInitialization("B");
        repeated.addInterval(1, 90, a);

        repeated.addInterval(0, 0, b);

        assertEquals(Arrays.asList(1, 90, 9), repeated.getRepetitionsList());
        List<Initialization> values = repeated.getInitializationsList();
        assertEquals(b, values.get(0));
        assertEquals(a, values.get(1));
        assertNull(values.get(2));
    }

    @Test
    void AddInterval_WhenUpperLimitExceeded_ClampsWithoutThrowing() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization value = new StubInitialization("X");

        assertDoesNotThrow(() -> repeated.addInterval(80, 150, value));

        assertEquals(Arrays.asList(80, 20), repeated.getRepetitionsList());
        List<Initialization> values = repeated.getInitializationsList();
        assertNull(values.get(0));
        assertEquals(value, values.get(1));
    }

    @Test
    void AddInterval_WhenCompletelyOutOfBounds_IgnoresSilently() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization value = new StubInitialization("X");

        assertDoesNotThrow(() -> repeated.addInterval(200, 300, value));
        assertDoesNotThrow(() -> repeated.addInterval(-50, -10, value));
        assertDoesNotThrow(() -> repeated.addInterval(50, 20, value)); // start > end

        assertEquals(Collections.singletonList(100), repeated.getRepetitionsList());
        assertNull(repeated.getInitializationsList().get(0));
    }

    @Test
    void AddInterval_WhenAdjacentSegmentsHaveSameInitialization_MergesSegments() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization a = new StubInitialization("A");
        repeated.addInterval(1, 90, a);

        repeated.addInterval(0, 0, a);

        assertEquals(Arrays.asList(91, 9), repeated.getRepetitionsList());
        assertEquals(a, repeated.getInitializationsList().get(0));
    }

    @Test
    void AddInterval_WhenRangeSpansMultipleSegments_ReplacesAllCoveredSegments() {
        RepeatedInitialization repeated = new RepeatedInitialization(100, null);
        StubInitialization a = new StubInitialization("A");
        StubInitialization b = new StubInitialization("B");
        StubInitialization c = new StubInitialization("C");
        StubInitialization x = new StubInitialization("X");

        repeated.addInterval(0, 9, a);   // 0..9
        repeated.addInterval(10, 19, b); // 10..19
        repeated.addInterval(20, 29, c); // 20..29

        repeated.addInterval(5, 25, x);

        assertEquals(Arrays.asList(5, 21, 4, 70), repeated.getRepetitionsList());
        List<Initialization> values = repeated.getInitializationsList();
        assertEquals(a, values.get(0)); // 0..4
        assertEquals(x, values.get(1)); // 5..25
        assertEquals(c, values.get(2)); // 26..29
        assertNull(values.get(3));      // 30..99, merged back into a single segment
    }

    @Test
    void Copy_WhenInvoked_ProducesIndependentInstanceWithSameLayout() {
        RepeatedInitialization repeated = new RepeatedInitialization(50, null);
        repeated.addInterval(0, 10, new StubInitialization("TEST"));

        Initialization copied = repeated.copy();

        assertInstanceOf(RepeatedInitialization.class, copied);
        RepeatedInitialization copiedRepeated = (RepeatedInitialization) copied;
        assertEquals(repeated.getDimension(), copiedRepeated.getDimension());
        assertEquals(repeated.getRepetitionsList(), copiedRepeated.getRepetitionsList());
    }
}