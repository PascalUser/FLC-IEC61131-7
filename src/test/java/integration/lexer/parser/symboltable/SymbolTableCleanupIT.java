package integration.lexer.parser.symboltable;

import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.enums.Use;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.supports.ParserTestSupport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SymbolTableCleanupIT extends ParserTestSupport {

    private static final String PROGRAM = String.join("\n",
            "FUNCTION_BLOCK main",
            "VAR_INPUT humidity : USINT; END_VAR",
            "VAR_OUTPUT heat : USINT; END_VAR",
            "FUZZIFY humidity TERM low := (0,1) (50,0); TERM high := (0,0) (50,1); END_FUZZIFY",
            "DEFUZZIFY heat TERM off := 0; TERM on := 255; METHOD : CoGS; DEFAULT := 0; END_DEFUZZIFY",
            "RULEBLOCK r AND : MIN; ACCU : MAX;",
            "RULE 1 : IF humidity IS low THEN heat IS off;",
            "END_RULEBLOCK",
            "END_FUNCTION_BLOCK");

    private static void assertNoUnknownUse(SymbolTable table) {
        Iterator<Map.Entry<String, LexemeInfo>> it = table.iterator();
        while (it.hasNext()) {
            Map.Entry<String, LexemeInfo> entry = it.next();
            assertNotEquals(Use.UNKNOWN, entry.getValue().use, "left over: " + entry.getKey());
        }
    }

    @Test
    void No_entry_with_unknown_use_remains_after_parsing() throws Exception {
        assertNoUnknownUse(parse(new StringReader(PROGRAM)).symbolTable());
    }

    @Test
    void Raw_identifiers_are_removed_and_declared_symbols_survive() throws Exception {
        SymbolTable table = parse(new StringReader(PROGRAM)).symbolTable();

        for (String raw : new String[]{"HUMIDITY", "HEAT", "LOW", "HIGH", "OFF", "ON", "R"}) {
            assertNull(table.get(raw), raw + " should have been cleaned");
        }
        assertNotNull(table.get("MAIN#HUMIDITY"));
        assertNotNull(table.get("MAIN#HUMIDITY#LOW"));
        assertNotNull(table.get("255"), "literals are kept");
    }

    @ParameterizedTest
    @MethodSource("examples")
    void Example_programs_leave_a_clean_table(File example) throws Exception {
        try (Reader reader = new FileReader(example)) {
            assertNoUnknownUse(parse(reader).symbolTable());
        }
    }

    static Stream<File> examples() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get("src/test/resources/examples"))) {
            return paths.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList()).stream();
        }
    }
}