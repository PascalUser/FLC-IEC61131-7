package unit.lexer.transformers;

import lexer.transformers.OmitLeadingZeroMagnitudes;
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
 * Tests for {@link OmitLeadingZeroMagnitudes} transformer.
 * <p>
 * Verifies that leading zero-valued magnitudes are removed from interval literals.
 * </p>
 *
 * @author Matias Ortiz
 * @version 1.0
 * @since 1.0
 */
class OmitLeadingZeroMagnitudesTest {

    private static Stream<Arguments> provideLexemes() {
        return Stream.of(
                Arguments.of("0h5m", "5m"),
                Arguments.of("1h0m5s", "1h0m5s"),
                Arguments.of("-0h5m", "-5m"),
                Arguments.of("0h0m0s", "0ms"),
                Arguments.of("5h", "5h"),
                Arguments.of("-1h0m0s", "-1h0m0s")
        );
    }

    @ParameterizedTest(name = "Original Lexeme: ''{0}'' -> Modified: ''{1}''")
    @MethodSource("provideLexemes")
    void Transform_LexemeWithZeroMagnitudes_ReturnsLexemeWithoutZeros(String input, String expected) {
        Transformer mockNext = Mockito.mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        OmitLeadingZeroMagnitudes sut = new OmitLeadingZeroMagnitudes(mockNext);

        String result = sut.transform(input);

        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "Original Lexeme: ''{0}'' -> Modified: ''{1}''")
    @MethodSource("provideLexemes")
    void Transform_AnyLexeme_DelegatesToNextTransformerInChain(String input, String expected) {
        Transformer mockNext = Mockito.mock(Transformer.class);
        OmitLeadingZeroMagnitudes sut = new OmitLeadingZeroMagnitudes(mockNext);

        sut.transform(input);

        verify(mockNext).transform(expected);
    }
}