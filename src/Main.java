import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import lexer_parser.Lexer;

public class Main {

    public static void main(String[] args) {
        Reader reader;
        try{
            reader = new FileReader("input.txt");
        } catch (Exception e) {
            System.err.println("File not found, using default input.");
            return;
        }
        Lexer lexer = new Lexer(reader);
        int lexema = 0;
        do {
            try {
                lexema = lexer.yylex();
            } catch (IOException ignored) {

            }
            System.out.println("Lexema: " + lexema + ", Valor: " + lexer.yytext());
        } while (lexema > 0);
    }
}