package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParserAndPublisherIT {

    @Test
    public void testParserPublishesOutputVariables() throws Exception {
        FileReader reader = new FileReader("src/test/resources/examples/program02.txt");

        SymbolTable st = new SymbolTable();
        Lexer lexer = new Lexer(reader, st);
        Parser parser = new Parser(lexer, st);

        parser.parse();

        LexemeInfo i1 = st.get("power");
        assertNotEquals(null, i1);
        assertEquals(ElementaryType.REAL, i1.type);
        assertEquals(Use.VARIABLE, i1.use);
        assertEquals(Source.OUT, i1.source);
    }
}
