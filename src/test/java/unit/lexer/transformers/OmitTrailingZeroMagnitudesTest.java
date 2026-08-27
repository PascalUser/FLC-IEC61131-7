package unit.lexer.transformers;

import lexer.transformers.OmitTrailingZeroMagnitudes;
import lexer.transformers.Transformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link OmitTrailingZeroMagnitudes} transformer.
 * <p>
 * Verifies that trailing zero-valued magnitudes are removed from interval literals.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
class OmitTrailingZeroMagnitudesTest {

    private static Stream<Arguments> provideLexemes() {
        return Stream.of(
                Arguments.of("1h5m0s", "1h5m"),
                Arguments.of("1h0m5s0ms", "1h0m5s"),
                Arguments.of("-1h0m0s", "-1h"),
                Arguments.of("0h0m0s", "0ms"),
                Arguments.of("5h", "5h"),
                Arguments.of("0h5m0s", "0h5m")
        );
    }

    @ParameterizedTest(name = "Original Lexeme: ''{0}'' -> Modified: ''{1}''")
    @MethodSource("provideLexemes")
    void Transform_LexemeWithZeroMagnitudes_ReturnsLexemeWithoutZeros(String input, String expected) {
        Transformer mockNext = Mockito.mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        OmitTrailingZeroMagnitudes sut = new OmitTrailingZeroMagnitudes(mockNext);

        String result = sut.transform(input);

        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "Original Lexeme: ''{0}'' -> Modified: ''{1}''")
    @MethodSource("provideLexemes")
    void Transform_AnyLexeme_DelegatesToNextTransformerInChain(String input, String expected) {
        Transformer mockNext = Mockito.mock(Transformer.class);
        OmitTrailingZeroMagnitudes sut = new OmitTrailingZeroMagnitudes(mockNext);

        sut.transform(input);

        verify(mockNext).transform(expected);
    }
}