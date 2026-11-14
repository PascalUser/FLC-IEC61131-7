package compiler.parser.facades;

import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.enums.Subtype;

import java.util.Objects;

public final class RightOperandResolver {
    public static String resolve(SymbolTable symbolTable, String leftOperand, String identifier) {
        String result = leftOperand + '#' + identifier;
        LexemeInfo leftInfo = symbolTable.get(leftOperand);
        while (leftInfo != null && leftInfo.subtype == Subtype.CUSTOM) {
            result = leftInfo.customType + '#' + identifier;
            leftInfo = symbolTable.get(leftInfo.customType);
            Objects.requireNonNull(leftInfo);
        }
        return result;
    }
}
