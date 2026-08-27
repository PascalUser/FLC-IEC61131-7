package unit.lexer.transformers;

import lexer.transformers.OmitTrailingZerosInMagnitudes;
import lexer.transformers.Transformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link OmitTrailingZerosInMagnitudes} transformer.
 * <p>
 * Verifies that trailing zeros in decimal parts of interval magnitudes are removed,
 * while integer trailing zeros, units, and signs are preserved.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
class OmitTrailingZerosInMagnitudesTest {

    private static final Transformer TRANSFORMER = new OmitTrailingZerosInMagnitudes(null);

    private static Stream<Arguments> provideValidInputs() {
        return Stream.of(
                // Decimal trailing zeros removal
                Arguments.of("1.500s", "1.5s"),
                Arguments.of("1.000s", "1s"),
                Arguments.of("0.050ms", "0.05ms"),
                Arguments.of("10.050h", "10.05h"),

                // Integer trailing zeros MUST be preserved
                Arguments.of("100s", "100s"),
                Arguments.of("10d20h30m", "10d20h30m"),
                Arguments.of("1000ms", "1000ms"),

                // Negative numbers
                Arguments.of("-1.50h", "-1.5h"),
                Arguments.of("-1.00s", "-1s"),
                Arguments.of("-100s", "-100s"),

                // Multiple magnitudes in a single string
                Arguments.of("1.500d2.000h3.050m4s", "1.5d2h3.05m4s"),
                Arguments.of("0.00d0.000h", "0d0h"),

                // No trailing zeros to remove (remains unchanged)
                Arguments.of("1.234s", "1.234s"),
                Arguments.of("1d2h3m", "1d2h3m"),

                // Mixed-case and multi-character units
                Arguments.of("0.50MS", "0.5MS"),
                Arguments.of("0.050us", "0.05us"),
                Arguments.of("1.000NS", "1NS")
        );
    }

    @ParameterizedTest(name = "{index}: Transform(\"{0}\") = \"{1}\"")
    @MethodSource("provideValidInputs")
    void Transform_ValidInput_RemovesTrailingZerosFromDecimalParts(String input, String expected) {
        String result = TRANSFORMER.transform(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "{index}: Transform(\"{0}\") delegates \"{1}\" to next in chain")
    @MethodSource("provideValidInputs")
    void Transform_AnyLexeme_DelegatesToNextTransformerInChain(String input, String expected) {
        Transformer mockNext = Mockito.mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        OmitTrailingZerosInMagnitudes sut = new OmitTrailingZerosInMagnitudes(mockNext);

        sut.transform(input);

        verify(mockNext).transform(expected);
    }

    @Test
    void Transform_EmptyString_ReturnsEmptyString() {
        String result = TRANSFORMER.transform("");
        assertEquals("", result);
    }

    @Test
    void Transform_NullInput_ReturnsNull() {
        Transformer mockNext = Mockito.mock(Transformer.class);
        when(mockNext.transform(null)).thenReturn(null);
        OmitTrailingZerosInMagnitudes sut = new OmitTrailingZerosInMagnitudes(mockNext);

        String result = sut.transform(null);

        assertNull(result);
    }
}