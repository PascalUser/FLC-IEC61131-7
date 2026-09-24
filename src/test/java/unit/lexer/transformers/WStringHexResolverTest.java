package unit.lexer.transformers;

import lexer.transformers.Transformer;
import lexer.transformers.hex_resolvers.WStringHexResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link WStringHexResolver}.
 */
class WStringHexResolverTest {

    private final WStringHexResolver resolver = new WStringHexResolver(null);

    private static Stream<Arguments> provideValidHexCases() {
        return Stream.of(
                Arguments.of("\"$0041\"", "\"A\""),
                Arguments.of("\"$0061\"", "\"a\""),
                Arguments.of("\"$000A\"", "\"\n\""),
                Arguments.of("\"$00FF\"", "\"ÿ\""),
                Arguments.of("\"$0041$0062$0063\"", "\"Abc\""),
                Arguments.of("\"test$0041$0062$0063end\"", "\"testAbcend\"")
        );
    }

    @ParameterizedTest(name = "{index}: {0} -> {1}")
    @MethodSource("provideValidHexCases")
    void transform_Valid4DigitHex_TransformsToUnicode(String input, String expected) {
        assertEquals(expected, resolver.transform(input));
    }

    @ParameterizedTest(name = "{index}: Input {0} is preserved intact")
    @ValueSource(strings = {
            "\"$GGGG\"",     // Caracteres no hexadecimales
            "\"$00G1\"",     // Mezcla de caracteres válidos e inválidos
            "\"$004\"",      // Incompleto (3 dígitos)
            "\"$41\"",       // Incompleto para WSTRING (sólo 2 dígitos, requiere 4)
            "\"$\"",         // Símbolo $ colgado
            "\"$$0041\"",    // Símbolo $ escapado (se preserva para el EscapeResolver)
            "\"hello\""      // Texto sin escapes
    })
    void transform_InvalidOrIncomplete_PassesThroughUnchanged(String input) {
        assertEquals(input, resolver.transform(input));
    }

    private static Stream<Arguments> provideDelegationCases() {
        return Stream.of(
                Arguments.of("\"$0041\"", "\"A\""),
                Arguments.of("\"$GGGG\"", "\"$GGGG\"")
        );
    }

    @ParameterizedTest(name = "{index}: {0} delegates \"{1}\" to next")
    @MethodSource("provideDelegationCases")
    void transform_ValidInput_DelegatesToNextInChain(String input, String expectedTransformed) {
        Transformer mockNext = mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(inv -> inv.getArgument(0));

        WStringHexResolver resolverWithChain = new WStringHexResolver(mockNext);
        resolverWithChain.transform(input);

        verify(mockNext, times(1)).transform(expectedTransformed);
    }
}