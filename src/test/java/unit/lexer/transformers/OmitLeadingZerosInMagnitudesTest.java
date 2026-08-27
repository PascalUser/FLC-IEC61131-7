package unit.lexer.transformers;

import lexer.transformers.OmitLeadingZerosInMagnitudes;
import lexer.transformers.Transformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link OmitLeadingZerosInMagnitudes} transformer.
 * <p>
 * This transformer removes leading zeros from numeric values in interval literals
 * while preserving all magnitude components (including zero-valued ones) and
 * their unit suffixes.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
class OmitLeadingZerosInMagnitudesTest {
    private static final Transformer TRANSFORMER = new OmitLeadingZerosInMagnitudes(null);

    @ParameterizedTest(name = "{index}: Transform(\"{0}\") = \"{1}\"")
    @MethodSource("provideValidInputs")
    void Transform_ValidInput_RemovesLeadingZerosFromEachMagnitude(String input, String expected) {
        String result = TRANSFORMER.transform(input);
        assertEquals(expected, result);
    }

    /**
     * Provides test cases covering various input patterns.
     *
     * @return stream of input/expected output pairs
     */
    private static Stream<Arguments> provideValidInputs() {
        return Stream.of(
                // Basic leading zeros removal - positive numbers with non-zero digits
                Arguments.of("0001H", "1H"),
                Arguments.of("005H", "5H"),
                Arguments.of("0123H", "123H"),
                Arguments.of("0001d0002h0003m0004s0005ms", "1d2h3m4s5ms"),
                Arguments.of("0001d2h03m4s05ms", "1d2h3m4s5ms"),
                Arguments.of("001d002h003m004s005ms", "1d2h3m4s5ms"),

                // Single magnitude with non-zero value
                Arguments.of("0005d", "5d"),
                Arguments.of("010h", "10h"),
                Arguments.of("005m", "5m"),
                Arguments.of("007s", "7s"),
                Arguments.of("009ms", "9ms"),

                // With decimal points (preserves zeros after decimal)
                Arguments.of("001.5d", "1.5d"),
                Arguments.of("010.05h", "10.05h"),
                Arguments.of("005.00m", "5.00m"),
                Arguments.of("007.000s", "7.000s"),
                Arguments.of("009.0005ms", "9.0005ms"),

                // Single digit (no leading zeros to remove)
                Arguments.of("1d", "1d"),
                Arguments.of("5h", "5h"),
                Arguments.of("9m", "9m"),
                Arguments.of("3s", "3s"),
                Arguments.of("7ms", "7ms"),

                // Zero with decimal
                Arguments.of("0.0d", "0.0d"),
                Arguments.of("00.00h", "0.00h"),
                Arguments.of("000.000m", "0.000m"),

                // Uppercase/lowercase units (preserved as-is)
                Arguments.of("0001h", "1h"),
                Arguments.of("0001m", "1m"),
                Arguments.of("0001s", "1s"),
                Arguments.of("0001ms", "1ms"),
                Arguments.of("0001D", "1D"),
                Arguments.of("0001M", "1M"),
                Arguments.of("0001S", "1S"),
                Arguments.of("0001MS", "1MS"),

                // Large numbers with many leading zeros
                Arguments.of("00000000000000000001H", "1H"),
                Arguments.of("0000000000123456789H", "123456789H"),

                // Zero-valued magnitudes are preserved (not skipped)
                Arguments.of("0000d0001h", "0d1h"),
                Arguments.of("0000d0000h0001m", "0d0h1m"),
                Arguments.of("0000d0000h0000m0001s", "0d0h0m1s"),
                Arguments.of("0000d0000h0000m0000s0001ms", "0d0h0m0s1ms"),
                Arguments.of("0d0h0m0s0ms", "0d0h0m0s0ms"),
                Arguments.of("0000d0000h0000m0000s0000ms", "0d0h0m0s0ms")
        );
    }

    @ParameterizedTest(name = "{index}: Transform(\"{0}\") is idempotent")
    @MethodSource("provideIdempotentInputs")
    void Transform_Idempotent_ApplyingTwiceYieldsSameResult(String input) {
        String firstPass = TRANSFORMER.transform(input);
        String secondPass = TRANSFORMER.transform(firstPass);
        assertEquals(firstPass, secondPass);
    }

    private static Stream<Arguments> provideIdempotentInputs() {
        return Stream.of(
                Arguments.of("0001H"),
                Arguments.of("01d02h03m"),
                Arguments.of("005ms"),
                Arguments.of("0000d0001h"),
                Arguments.of("-001d002h"),
                Arguments.of("001.5d"),
                Arguments.of("0d0h0m0s0ms")
        );
    }

    @Test
    void Transform_EmptyString_ReturnsEmptyString() {
        String result = TRANSFORMER.transform("");
        assertEquals("", result);
    }

    @Test
    void Transform_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TRANSFORMER.transform(null));
    }

    @Test
    void Transform_OnlyUnitNoNumber_PreservedAsIs() {
        // Edge cases: unit without preceding number
        assertEquals("H", TRANSFORMER.transform("H"));
        assertEquals("d", TRANSFORMER.transform("d"));
        assertEquals("ms", TRANSFORMER.transform("ms"));
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void Transform_NonMagnitudeCharacters_LeadingZerosStillRemoved() {
        assertEquals("abc1Hxyz", TRANSFORMER.transform("abc0001Hxyz"));
        assertEquals("prefix_1H_suffix", TRANSFORMER.transform("prefix_0001H_suffix"));
    }

    @Test
    void Transform_AllZeroMagnitudes_PreservesAllMagnitudesWithSingleZero() {
        assertEquals("0d", TRANSFORMER.transform("0000d"));
        assertEquals("0h", TRANSFORMER.transform("0000h"));
        assertEquals("0m", TRANSFORMER.transform("0000m"));
        assertEquals("0s", TRANSFORMER.transform("0000s"));
        assertEquals("0ms", TRANSFORMER.transform("0000ms"));
        assertEquals("0d0h0m0s0ms", TRANSFORMER.transform("0d0h0m0s0ms"));
        assertEquals("0d0h0m0s0ms", TRANSFORMER.transform("0000d0000h0000m0000s0000ms"));
    }

    @Test
    void Transform_ZeroMagnitudesFollowedByNonZero_PreservesAllMagnitudes() {
        assertEquals("0d1h", TRANSFORMER.transform("0000d0001h"));
        assertEquals("0d0h1m", TRANSFORMER.transform("0000d0000h0001m"));
        assertEquals("0d0h0m1s", TRANSFORMER.transform("0000d0000h0000m0001s"));
        assertEquals("0d0h0m0s1ms", TRANSFORMER.transform("0000d0000h0000m0000s0001ms"));
    }

    @Test
    void Transform_NegativeSign_LeadingZerosRemovedAfterSign() {
        assertEquals("-1d2h", TRANSFORMER.transform("-001d002h"));
        assertEquals("-5h", TRANSFORMER.transform("-0005h"));
        assertEquals("-0d0h0m", TRANSFORMER.transform("-00d00h00m"));
    }

    @Test
    void Transform_LargeNumbersWithManyLeadingZeros_RemovesAllLeadingZeros() {
        assertEquals("1H", TRANSFORMER.transform("00000000000000000001H"));
        assertEquals("123456789H", TRANSFORMER.transform("0000000000123456789H"));
    }

    @Test
    void Transform_MixedCaseUnits_Preserved() {
        assertEquals("1h", TRANSFORMER.transform("0001h"));
        assertEquals("1H", TRANSFORMER.transform("0001H"));
        assertEquals("1ms", TRANSFORMER.transform("0001ms"));
        assertEquals("1MS", TRANSFORMER.transform("0001MS"));
    }

    @Test
    void Transform_ComplexWithDecimalsAndMultipleMagnitudes() {
        assertEquals("1d2h3m4s5ms", TRANSFORMER.transform("0001d0002h0003m0004s0005ms"));
        assertEquals("1.5d", TRANSFORMER.transform("001.5d"));
        assertEquals("10.05h", TRANSFORMER.transform("010.05h"));
    }

    @ParameterizedTest(name = "{index}: Transform(\"{0}\") delegates \"{1}\" to next in chain")
    @MethodSource("provideValidInputs")
    void Transform_AnyLexeme_DelegatesToNextTransformerInChain(String input, String expected) {
        // Arrange
        Transformer mockNext = Mockito.mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        OmitLeadingZerosInMagnitudes sut = new OmitLeadingZerosInMagnitudes(mockNext);

        // Act
        sut.transform(input);

        // Assert
        verify(mockNext).transform(expected);
    }
}