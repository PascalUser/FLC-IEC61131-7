package lexer.semantics;

import parser.Parser.Lexer;
import utils.builders.LexemeInfoBuilder;
import utils.enums.Subtype;
import utils.LexemeInfo;
import utils.SymbolTable;

public class Default implements SemanticAnalyzer {
    private final SymbolTable symbolTable;

    public Default(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Result analyze(String lexeme, Subtype subtype) {
        LexemeInfo info = this.symbolTable.get(lexeme);
        if (info == null) {
            this.symbolTable.put(lexeme, new LexemeInfoBuilder().build());
        }
        return new Result(lexeme, this.translate(subtype));
    }

    private int translate(Subtype subtype) {
        return switch (subtype) {
            case UNKNOWN, CUSTOM-> -1;
            case STRING, WSTRING -> Lexer.STRING_LITERAL;
            case TIME, DATE, TIME_OF_DAY, DATE_AND_TIME -> Lexer.TIME_LITERAL;
            case BOOL, SINT, INT, DINT, LINT, USINT, UINT, UDINT, ULINT,
                 REAL, LREAL, BYTE, WORD, DWORD, LWORD  -> Lexer.NUMERIC_LITERAL;
        };
    }
}
