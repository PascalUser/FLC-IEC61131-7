package parser;

import lexer.Lexer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.SymbolTable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    static Stream<File> exampleFileProvider() {
        try {
            Path resourcesPath = Paths.get("src/test/resources/examples");
            return Files.walk(resourcesPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile);
        } catch (IOException e) {
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
            System.out.println("Probando archivo: " + exampleFile.getName());

        } catch (Exception e) {
            fail("Falló el test con el archivo: " + exampleFile.getName() + " debido a: " + e.getMessage());
        }
    }
}