package unit.parser;

import lexer.Lexer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import utils.SymbolTable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    static Stream<File> exampleFileProvider() {
        Path resourcesPath = Paths.get("src/test/resources/examples");
        try (Stream<Path> paths = Files.walk(resourcesPath)) {
            return paths.filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .toList()
                        .stream();
        }
        catch (IOException e) {
            throw new RuntimeException("Program examples were not found in: " + e.getMessage());
        }
    }
    @ParameterizedTest
    @MethodSource("exampleFileProvider")
    void testProgramExamples(File exampleFile) {
        try (FileReader reader = new FileReader(exampleFile)) {
            SymbolTable st = new SymbolTable();
            Lexer lexer = new Lexer(reader, st);
            Parser parser = new Parser(lexer, st);
            assertTrue(parser.parse());
        } catch (Exception e) {
            fail("Test failed with file: " + exampleFile.getName() + " due to: " + e.getMessage());
        }
    }
}