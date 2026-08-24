package unit.parser;

import lexer.Lexer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import utils.DiagnosticsHandler;
import utils.SymbolTable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bison-generated {@link Parser}.
 * <p>
 * Tests parsing of syntactically valid IEC 61131-7 example programs
 * from the test resources directory.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
class ParserTest {

    private static Stream<File> exampleFileProvider() {
        Path resourcesPath = Paths.get("src/test/resources/examples");
        try (Stream<Path> paths = Files.walk(resourcesPath)) {
            return paths.filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList())
                        .stream();
        }
        catch (IOException e) {
            throw new RuntimeException("Program examples were not found in: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("exampleFileProvider")
    void Parse_ForSyntacticallyValidPrograms_IsTrue(File exampleFile) {
        try (FileReader reader = new FileReader(exampleFile)) {
            SymbolTable st = new SymbolTable();
            DiagnosticsHandler dh = new DiagnosticsHandler();
            Lexer lexer = new Lexer(reader, st, dh);
            Parser parser = new Parser(lexer, st);
            assertTrue(parser.parse());
            assertFalse(dh.hasErrors());
        } catch (Exception e) {
            fail("Test failed with file: " + exampleFile.getName() + " due to: " + e.getMessage());
        }
    }
}