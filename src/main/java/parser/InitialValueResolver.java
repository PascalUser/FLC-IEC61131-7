package parser;

import utils.SymbolTable;

/* TODO: clase que con un switch case puede determinar el valor inicial de una variable de tipo primitivo
 *   sin inicializar
 * */
public class InitialValueResolver {

    public InitialValueResolver(SymbolTable symbolTable) { }

    public String resolve(int tokenNumber) {
        switch (tokenNumber) {
            case Parser.Lexer.BOOL:
            case Parser.Lexer.REAL:
                /* TODO: Para evitar reutilizar Reals() se puede separar la lógica del Lexer que da de alta
                 *  las constantes en la tabla de símbolos. La razón se debe a que el Parser no debería conocer
                 *  cómo el léxico agrega las constantes a la tabla => codigo fragil porque requiere cambiar la logica
                 *  en varios lados del codigo.
                 * */
                // new Reals().analyze(lexicalContext("0.0", symbolTable, diagnosticHandler)).lexeme;
                return "";
            default:
                throw new IllegalArgumentException();
        }
    }
}
