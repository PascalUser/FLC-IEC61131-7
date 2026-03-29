package lexer;

import parser.Parser;
import utils.ElementaryType;
import utils.LexemeInfo;
import utils.SymbolTable;

public class GenericAnalyzer implements SemanticAnalyzer {
    private final SymbolTable symbolTable;

    public GenericAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Result analyze(String lexeme, ElementaryType type) {
        LexemeInfo info = this.symbolTable.get(lexeme);
        if (info == null) {
            this.symbolTable.put(lexeme, new LexemeInfo());
        }
        return new Result(lexeme, this.translate(type));
    }

    private int translate(ElementaryType type) {
        return switch (type) {
            case UNKNOWN -> -1;
            case STRING, WSTRING -> Parser.Lexer.STRING_LITERAL;

            case BOOL,
                 SINT, INT, DINT, LINT,
                 USINT, UINT, UDINT, ULINT,
                 REAL, LREAL,
                 TIME, DATE, TIME_OF_DAY, DATE_AND_TIME,
                 BYTE, WORD, DWORD, LWORD
                    -> Parser.Lexer.NUMERIC_LITERAL;
        };
    }
}
