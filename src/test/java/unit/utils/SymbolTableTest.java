package unit.utils;

import org.junit.jupiter.api.Test;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import static org.junit.jupiter.api.Assertions.*;

// TODO: chequear valores precargados

/**
 * Unit tests for {@link SymbolTable}.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
class SymbolTableTest {

    @Test
    void putAndGet_StoresAndRetrievesLexemeInfo() {
        SymbolTable table = new SymbolTable();
        LexemeInfo info = new LexemeInfo(Type.SIMPLE, Subtype.INT, null, Use.VARIABLE,
            Source.IN, null, null, null, 42);

        LexemeInfo previous = table.put("testVar", info);

        assertNull(previous);
        assertSame(info, table.get("testVar"));
    }

    @Test
    void get_ForNonExistentKey_ReturnsNull() {
        SymbolTable table = new SymbolTable();

        assertNull(table.get("nonexistent"));
    }

    @Test
    void put_OverwritesExistingKey_ReturnsOldValue() {
        SymbolTable table = new SymbolTable();
        LexemeInfo first = new LexemeInfo(Type.SIMPLE, Subtype.INT, null, Use.VARIABLE,
            Source.IN, null, null, null, 1);
        LexemeInfo second = new LexemeInfo(Type.SIMPLE, Subtype.REAL, null, Use.VARIABLE,
            Source.IN, null, null, null, 2.0);

        table.put("var", first);
        LexemeInfo previous = table.put("var", second);

        assertSame(first, previous);
        assertSame(second, table.get("var"));
    }
}

