package unit.lexer.transformers;

import lexer.transformers.StringEscapeResolver;
import lexer.transformers.Transformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link StringEscapeResolver}.
 */
class StringEscapeResolverTest {

    private final StringEscapeResolver resolver = new StringEscapeResolver(null);

    @ParameterizedTest(name = "{index}: STRING {0} -> {1}")
    @CsvSource(delimiter = '|', value = {
            "'hello$$world'   | 'hello$world'",
            "'hello$'world'   | 'hello'world'",
            "'hello$\"world'  | 'hello\"world'",
            "'hello$Nworld'   | 'hello\nworld'",
            "'hello$nworld'   | 'hello\nworld'",
            "'hello$Lworld'   | 'hello\nworld'",
            "'hello$Rworld'   | 'hello\rworld'",
            "'hello$Pworld'   | 'hello\fworld'",
            "'hello$Tworld'   | 'hello\tworld'",
            "'hello$l$tworld' | 'hello\n\tworld'",
            "'hello$Nworld'   | 'hello\nworld'"
    })
    void transform_SingleByteStringValidEscapes_ResolvesCorrectly(String input, String expected) {
        assertEquals(expected, resolver.transform(input));
    }

    private static Stream<Arguments> provideWStringEscapes() {
        return Stream.of(
                Arguments.of("\"hello$$world\"",   "\"hello$world\""),
                Arguments.of("\"hello$'world\"",   "\"hello'world\""),
                Arguments.of("\"hello$\"world\"",  "\"hello\"world\""),
                Arguments.of("\"hello$Nworld\"",   "\"hello\nworld\""),
                Arguments.of("\"hello$nworld\"",   "\"hello\nworld\""),
                Arguments.of("\"hello$Lworld\"",   "\"hello\nworld\""),
                Arguments.of("\"hello$Rworld\"",   "\"hello\rworld\""),
                Arguments.of("\"hello$Pworld\"",   "\"hello\fworld\""),
                Arguments.of("\"hello$Tworld\"",   "\"hello\tworld\""),
                Arguments.of("\"hello$n$rworld\"", "\"hello\n\rworld\"")
        );
    }

    @ParameterizedTest(name = "{index}: WSTRING {0} -> {1}")
    @MethodSource("provideWStringEscapes")
    void transform_WStringEscapes_ResolvesCorrectly(String input, String expected) {
        assertEquals(expected, resolver.transform(input));
    }

    @ParameterizedTest(name = "{index}: Input {0} is preserved intact")
    @ValueSource(strings = {
            "''",
            "\"\"",
            "'hello'",
            "\"hello\"",
            "'hello$0041'",
            "\"hello$0041\"",
            "'hello$Xworld'",
            "\"hello$Zworld\""
    })
    void transform_NoActionNeeded_ReturnsInputUnchanged(String input) {
        assertEquals(input, resolver.transform(input));
    }

    @ParameterizedTest(name = "{index}: {0} delegates \"{1}\" to next transformer")
    @CsvSource(delimiter = '|', value = {
            "'hello$Nworld'   | 'hello\nworld'",
            "\"hello$Tworld\" | \"hello\tworld\"",
            "'hello$0041'     | 'hello$0041'"
    })
    void transform_AnyInput_DelegatesTransformedResultToNextInChain(String input, String expectedTransformed) {
        Transformer mockNext = mock(Transformer.class);
        when(mockNext.transform(anyString())).thenAnswer(invocation -> invocation.getArgument(0));

        StringEscapeResolver resolverWithChain = new StringEscapeResolver(mockNext);

        resolverWithChain.transform(input);

        verify(mockNext, times(1)).transform(expectedTransformed);
    }
}