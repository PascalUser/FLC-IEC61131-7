package unit.lexer.transformers;

import lexer.transformers.hex_resolvers.StringHexResolver;
import lexer.transformers.Transformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link StringHexResolver}.
 */
class StringHexResolverTest {

    private final StringHexResolver resolver = new StringHexResolver(null);

    private static Stream<Arguments> provideValidHexCases() {
        return Stream.of(
                Arguments.of("'$41'", "'A'"),
                Arguments.of("'$61'", "'a'"),
                Arguments.of("'$0A'", "'\n'"),
                Arguments.of("'$FF'", "'ÿ'"),
                Arguments.of("'$41$62$63'", "'Abc'"),
                Arguments.of("'hello$41$62$63world'", "'helloAbcworld'")
        );
    }

    @ParameterizedTest(name = "{index}: {0} -> {1}")
    @MethodSource("provideValidHexCases")
    void transform_Valid2DigitHex_TransformsToAscii(String input, String expected) {
        assertEquals(expected, resolver.transform(input));
    }

    @ParameterizedTest(name = "{index}: Input {0} is preserved intact")
    @ValueSource(strings = {
            "'$GG'",
            "'$G1'",
            "'$4'",
            "'$'",
            "'$$41'",
            "'hello'"
    })
    void transform_InvalidOrIncomplete_PassesThroughUnchanged(String input) {
        assertEquals(input, resolver.transform(input));
    }

    private static Stream<Arguments> provideDelegationCases() {
        return Stream.of(
                Arguments.of("'$41'", "'A'"),
                Arguments.of("'$GG'", "'$GG'")
        );
    }

    @ParameterizedTest(name = "{index}: {0} delegates \"{1}\" to next")
    @MethodSource("provideDelegationCases")
    void transform_ValidInput_DelegatesToNextInChain(String input, String expectedTransformed) {
        Transformer mockNext = mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(inv -> inv.getArgument(0));

        StringHexResolver resolverWithChain = new StringHexResolver(mockNext);
        resolverWithChain.transform(input);

        verify(mockNext, times(1)).transform(expectedTransformed);
    }
}