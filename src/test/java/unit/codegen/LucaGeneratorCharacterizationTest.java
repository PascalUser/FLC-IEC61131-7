package unit.codegen;

import codegen.CodeGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.LucaDslReader;
import utils.TestResources;

import static utils.CppAssertions.assertSameText;

/**
 * Characterization of the port of Luca Sarramone's code generator: fed with his own examples (read from his
 * DSL, as his parser would), the ported generator must produce exactly the text of his original tool, except for
 * line endings. See {@code src/test/resources/golden/luca/README.md}.
 */
class LucaGeneratorCharacterizationTest {

    private static final String ROOT = "golden/luca/";

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void Port_generates_the_same_text_as_the_original_generator(String example) throws Exception {
        String dsl = TestResources.read(ROOT + example + "/" + example + ".txt");
        CodeGenerator generator = new CodeGenerator(LucaDslReader.read(dsl));
        String expected = ROOT + example + "/expected/" + example;

        assertSameText(example + "_out.cpp", TestResources.read(expected + "_out.cpp"), generator.generateCpp());
        assertSameText(example + "_out.h", TestResources.read(expected + "_out.h"), generator.generateHeader());
        assertSameText(example + "_outTB.cpp", TestResources.read(expected + "_outTB.cpp"),
                generator.generateTestBench(example + "_out.h"));
    }
}
