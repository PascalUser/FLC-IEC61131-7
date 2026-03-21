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
        int lexeme = 0;
        do {
            try {
                lexeme = lexer.yylex();
            } catch (IOException ignored) {

            }
            System.out.println("lexeme: " + lexeme + ", Valor: " + lexer.yytext());
        } while (lexeme > 0);
    }
}