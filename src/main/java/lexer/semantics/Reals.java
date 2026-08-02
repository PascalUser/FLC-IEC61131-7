package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.RealOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public class Reals implements SemanticAnalyzer {

    public Result analyze(LexicalContext lc) {
        String lexeme = lc.lexeme().replace("_", "");

        Double initialValue;
        Subtype subtype;
        try {
            initialValue = Double.parseDouble(lexeme);
            subtype = getRange(initialValue);
        } catch (NumberFormatException e) {
            initialValue = null;
            subtype = Subtype.UNKNOWN;
        }

        if (subtype == Subtype.UNKNOWN) {
            lc.diagnosticsHandler().add(new RealOutOfRange(lc.line(), lexeme));

            // Error Correction
            boolean isNegative = lexeme.startsWith("-");
            initialValue = isNegative ? -Double.MAX_VALUE : Double.MAX_VALUE;
            lexeme = Double.toString(initialValue);
            subtype = Subtype.LREAL;
        }

        if (lc.symbolTable().get(lexeme) == null) {
            lc.symbolTable().put(
                lexeme,
                new LexemeInfoBuilder()
                        .type(Type.SIMPLE)
                        .subtype(subtype)
                        .use(Use.LITERAL)
                        .initialValue(initialValue)
                        .build()
            );
        }

        return new Result(lexeme, Parser.Lexer.NUMERIC_LITERAL);
    }

    private Subtype getRange(double value) {
        if (Double.isInfinite(value)) return Subtype.UNKNOWN;
        double abs = Math.abs(value);
        if (abs <= Float.MAX_VALUE) return Subtype.REAL;
        return Subtype.LREAL;
    }
}