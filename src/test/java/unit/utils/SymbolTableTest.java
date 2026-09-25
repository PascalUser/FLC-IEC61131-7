package unit.utils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.builders.LexemeInfoBuilder;
import utils.enums.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SymbolTable}.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
@Tag("utils")
class SymbolTableTest {

    /**
     * Provides various {@link LexemeInfo} instances covering different type/subtype/use/source combinations
     * for parameterized testing of {@code put()} and {@code get()}.
     *
     * @return stream of (LexemeInfo, description) pairs
     */
    private static Stream<Arguments> ProvideLexemeInfos() {
        return Stream.of(
                Arguments.of(new LexemeInfoBuilder()
                                .type(Type.SIMPLE)
                                .subtype(Subtype.INT)
                                .use(Use.VARIABLE)
                                .source(Source.IN)
                                .initialValue(42)
                                .build()
                ),
                Arguments.of(new LexemeInfoBuilder()
                                .type(Type.ARRAY)
                                .subtype(Subtype.REAL)
                                .use(Use.FIELD)
                                .source(Source.OUT)
                                .inferiorLimits(Collections.singletonList("0"))
                                .superiorLimits(Collections.singletonList("10"))
                                .initialValue(3.14)
                                .build()
                ),
                Arguments.of(new LexemeInfoBuilder()
                                .type(Type.STRUCT)
                                .subtype(Subtype.CUSTOM)
                                .customType("MyStruct")
                                .use(Use.FUNCTION)
                                .source(Source.FUZZIFY)
                                .parameters(Arrays.asList("param1", "param2"))
                                .build()
                ),
                Arguments.of(new LexemeInfoBuilder()
                                .type(Type.SIMPLE)
                                .subtype(Subtype.BOOL)
                                .use(Use.LITERAL)
                                .source(Source.DEFUZZIFY)
                                .initialValue(true)
                                .build()
                ),
                Arguments.of(new LexemeInfoBuilder()
                                .type(Type.SIMPLE)
                                .subtype(Subtype.STRING)
                                .use(Use.VARIABLE)
                                .source(Source.IN)
                                .initialValue("test")
                                .build()
                )
        );
    }

    /**
     * Provides test cases for {@link SymbolTable#putIfAbsent(String, LexemeInfo)} behavior.
     *
     * @return stream of (key, toInsert, preExisting, expectedStored, description) tuples
     */
    private static Stream<Arguments> ProvidePutIfAbsentCases() {
        LexemeInfo first = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(1)
                .build();
        LexemeInfo second = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(2.0)
                .build();

        return Stream.of(
                Arguments.of("newKey", first, null, first, "absent key inserts and returns null"),
                Arguments.of("existingKey", first, first, first, "existing key returns old value"),
                Arguments.of("existingKey", second, first, first, "existing key does not overwrite")
        );
    }

    /**
     * Provides test cases for {@link SymbolTable#size()} with different initial entry counts.
     *
     * @return stream of (initialEntries, expectedSize, description) tuples
     */
    private static Stream<Arguments> ProvideSizeCases() {
        Map<String, LexemeInfo> twoEntries = new HashMap<>();
        twoEntries.put("k1", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(1)
                .build());
        twoEntries.put("k2", new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(2.0)
                .build());

        return Stream.of(
                Arguments.of(Collections.emptyMap(), 0, "empty table"),
                Arguments.of(Collections.singletonMap("k1", new LexemeInfoBuilder()
                        .type(Type.SIMPLE)
                        .subtype(Subtype.INT)
                        .use(Use.VARIABLE)
                        .source(Source.IN)
                        .initialValue(1)
                        .build()), 1, "single entry"),
                Arguments.of(twoEntries, 2, "two entries")
        );
    }

    @Test
    void PutAndGet_StoresAndRetrievesLexemeInfo() {
        SymbolTable table = new SymbolTable();
        LexemeInfo info = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(42)
                .build();

        LexemeInfo previous = table.put("testVar", info);

        assertAll("put and get",
                () -> assertNull(previous, "put on absent key returns null"),
                () -> assertSame(info, table.get("testVar"), "get retrieves stored value")
        );
    }

    @ParameterizedTest(name = "{index}: {4}")
    @MethodSource("ProvideLexemeInfos")
    void Put_WithVariousLexemeInfo_StoresCorrectly(LexemeInfo info) {
        SymbolTable table = new SymbolTable();

        LexemeInfo previous = table.put("var", info);

        assertAll("put stores LexemeInfo",
                () -> assertNull(previous),
                () -> assertSame(info, table.get("var"))
        );
    }

    @Test
    void Get_ForNonExistentKey_ReturnsNull() {
        SymbolTable table = new SymbolTable();

        LexemeInfo result = table.get("nonexistent");

        assertNull(result);
    }

    @Test
    void Put_OverwritesExistingKey_ReturnsOldValue() {
        SymbolTable table = new SymbolTable();
        LexemeInfo first = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(1)
                .build();
        LexemeInfo second = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(2.0)
                .build();

        table.put("var", first);
        LexemeInfo previous = table.put("var", second);

        assertAll("put overwrites existing key",
                () -> assertSame(first, previous, "returns old value"),
                () -> assertSame(second, table.get("var"), "stores new value")
        );
    }

    @ParameterizedTest(name = "{index}: putIfAbsent({0}) -> returns {1}, stores {2} ({3})")
    @MethodSource("ProvidePutIfAbsentCases")
    void PutIfAbsent_Behavior(String key, LexemeInfo toInsert, LexemeInfo preExisting, LexemeInfo expectedStored) {
        SymbolTable table = new SymbolTable();
        if (preExisting != null) {
            table.put(key, preExisting);
        }

        LexemeInfo previous = table.putIfAbsent(key, toInsert);

        assertAll("putIfAbsent behavior",
                () -> assertSame(expectedStored, table.get(key), "stored value is correct"),
                () -> {
                    if (preExisting == null) {
                        assertNull(previous, "returns null for absent key");
                    } else {
                        assertSame(preExisting, previous, "returns existing value");
                    }
                }
        );
    }

    @Test
    void PutIfAbsent_NullValue_StoresNull() {
        SymbolTable table = new SymbolTable();

        LexemeInfo previous = table.putIfAbsent("key", null);

        assertAll("putIfAbsent with null value",
                () -> assertNull(previous),
                () -> assertNull(table.get("key"))
        );
    }

    @ParameterizedTest(name = "{index}: size() = {1} for {2}")
    @MethodSource("ProvideSizeCases")
    void Size_ReturnsEntryCount(java.util.Map<String, LexemeInfo> initialEntries, int expectedSize) {
        SymbolTable table = new SymbolTable();
        initialEntries.forEach(table::put);

        int actualSize = table.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void Size_AfterPutIfAbsent_IncrementsOnlyForNewKeys() {
        SymbolTable table = new SymbolTable();
        LexemeInfo info1 = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.INT)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(1)
                .build();
        LexemeInfo info2 = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.REAL)
                .use(Use.VARIABLE)
                .source(Source.IN)
                .initialValue(2.0)
                .build();

        assertAll("size after putIfAbsent",
                () -> assertEquals(0, table.size(), "initially empty"),
                () -> {
                    table.putIfAbsent("key1", info1);
                    assertEquals(1, table.size(), "after first insert");
                },
                () -> {
                    table.putIfAbsent("key1", info2);
                    assertEquals(1, table.size(), "after duplicate insert");
                },
                () -> {
                    table.putIfAbsent("key2", info2);
                    assertEquals(2, table.size(), "after second unique insert");
                }
        );
    }
}