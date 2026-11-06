package unit.parser.facades;

import compiler.parser.facades.SymbolTableCleaner;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Use;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableCleanerTest {
    @Test
    void Clean_removes_only_entries_with_unknown_use() {
        SymbolTable table = new SymbolTable();
        table.put("X", new LexemeInfoBuilder().use(Use.UNKNOWN).build());
        table.put("1", new LexemeInfoBuilder().use(Use.LITERAL).build());

        SymbolTableCleaner.clean(table);

        assertNull(table.get("X"));
        assertNotNull(table.get("1"));
        assertEquals(1, table.size());
    }
}
