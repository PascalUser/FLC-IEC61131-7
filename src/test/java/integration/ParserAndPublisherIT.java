package integration;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class ParserAndPublisherIT {

    @Test
    public void testParserPublishesOutputVariables() throws Exception {
        FileReader reader = new FileReader("src/test/resources/examples/program02.txt");
        SymbolTable st = new SymbolTable();
        Lexer lexer = new Lexer(reader, st);
        Parser parser = new Parser(lexer, st);

        boolean valid = parser.parse();
        assertTrue(valid);

        LexemeInfo i1 = st.get("power");
        assertNotEquals(null, i1);
        assertEquals(Type.SIMPLE, i1.type);
        assertEquals(Subtype.REAL, i1.subtype);
        assertEquals(Use.VARIABLE, i1.use);
        assertEquals(Source.OUT, i1.source);
    }

    @Test
    public void testParserPublishesEnumerateVariables() throws Exception {
        FileReader reader = new FileReader("src/test/resources/examples/program03.txt");
        SymbolTable st = new SymbolTable();
        Lexer lexer = new Lexer(reader, st);
        Parser parser = new Parser(lexer, st);

        boolean valid = parser.parse();
        assertTrue(valid);

        LexemeInfo i1 = st.get("Machine1");
        assertNotEquals(null, i1);
        assertEquals(Type.SIMPLE, i1.type);
        assertEquals(Subtype.CUSTOM, i1.subtype);
        assertEquals(Use.VARIABLE, i1.use);
        assertEquals(Source.NONE, i1.source);
        assertEquals("STOPPED", i1.initialValue);

        LexemeInfo i2 = st.get("ValveA");
        assertNotEquals(null, i2);
        assertEquals(Type.SIMPLE, i2.type);
        assertEquals(Subtype.CUSTOM, i2.subtype);
        assertEquals(Use.VARIABLE, i2.use);
        assertEquals(Source.NONE, i2.source);
        assertEquals("OPEN", i2.initialValue);
    }

    @Test
    public void testParserPublishesSubrangeVariables() throws Exception {
        FileReader reader = new FileReader("src/test/resources/examples/program03.txt");
        SymbolTable st = new SymbolTable();
        Lexer lexer = new Lexer(reader, st);
        Parser parser = new Parser(lexer, st);

        boolean valid = parser.parse();
        assertTrue(valid);

        // TODO: hacer que el lexico agregue los initialValue de los numeros
        // TODO: hacer tests unitarios que verifiquen las restricciones lexicas y los initialValue
        LexemeInfo i1 = st.get("Level");
        assertNotEquals(null, i1);
        assertEquals(Type.SIMPLE, i1.type);
        assertEquals(Subtype.CUSTOM, i1.subtype);
        assertEquals("TANK_LEVEL", i1.customType);
        assertEquals(Use.VARIABLE, i1.use);
        assertEquals(Source.NONE, i1.source);
        assertEquals(50, i1.initialValue);

        LexemeInfo i2 = st.get("TANK_LEVEL");
        assertEquals(Type.SUBRANGE, i2.type);
        assertEquals(Subtype.INT, i2.subtype);
        assertEquals(Use.TYPE, i2.use);
        assertEquals(Source.UNKNOWN, i2.source);
        assertEquals(0, i2.inferiorLimit);
        assertEquals(100, i2.superiorLimit);

        LexemeInfo i3 = st.get("FREQUENCY_HZ");
        assertEquals(Type.SUBRANGE, i3.type);
        assertEquals(Subtype.INT, i3.subtype);
        assertEquals(Use.TYPE, i3.use);
        assertEquals(Source.UNKNOWN, i3.source);
        assertEquals(0, i3.inferiorLimit);
        assertEquals(60, i3.superiorLimit);
        assertEquals(25, i1.initialValue);
    }

}
