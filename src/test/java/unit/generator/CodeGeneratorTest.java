package unit.generator;

import compiler.generator.CodeGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import doubles.ControllerLowerStub;
import utils.TestResources;
import utils.supports.CodeGeneratorTestSupport;

public class CodeGeneratorTest extends CodeGeneratorTestSupport {

    private static final String ROOT = "golden/luca/";

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void GenerateCpp_WithOriginalDslExamples_MatchesReferenceImplementation(String example) throws Exception {
        String dsl = TestResources.read(ROOT + example + "/" + example + ".txt");
        CodeGenerator generator = new CodeGenerator(ControllerLowerStub.read(dsl));
        String expectedCpp = TestResources.read(ROOT + example + "/expected/" + example + "_out.cpp");

        String actualCpp = generator.generateCpp();

        assertSameText(example + "_out.cpp", expectedCpp, actualCpp);
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void GenerateHeader_WithOriginalDslExamples_MatchesReferenceHeader(String example) throws Exception {
        String dsl = TestResources.read(ROOT + example + "/" + example + ".txt");
        CodeGenerator generator = new CodeGenerator(ControllerLowerStub.read(dsl));
        String expectedHeader = TestResources.read(ROOT + example + "/expected/" + example + "_out.h");

        String actualHeader = generator.generateHeader();

        assertSameText(example + "_out.h", expectedHeader, actualHeader);
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"pendulo_invertido", "Autoenfoque", "EstacionamientoCamion"})
    void GenerateTestBench_WithOriginalDslExamples_MatchesReferenceTestBench(String example) throws Exception {
        String dsl = TestResources.read(ROOT + example + "/" + example + ".txt");
        CodeGenerator generator = new CodeGenerator(ControllerLowerStub.read(dsl));
        String expectedTestBench = TestResources.read(ROOT + example + "/expected/" + example + "_outTB.cpp");
        String headerFilename = example + "_out.h";

        String actualTestBench = generator.generateTestBench(headerFilename);

        assertSameText(example + "_outTB.cpp", expectedTestBench, actualTestBench);
    }
}