package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.IntegerOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public class Integers implements SemanticAnalyzer {
    private static final long MIN_SINT = -128L;
    private static final long MAX_SINT = 127L;

    private static final long MIN_INT = -32768L;
    private static final long MAX_INT = 32767L;

    private static final long MIN_DINT = -2147483648L;
    private static final long MAX_DINT = 2147483647L;

    private static final long MIN_LINT = Long.MIN_VALUE;
    private static final long MAX_LINT = Long.MAX_VALUE;

    @Override
    public Result analyze(LexicalContext lc) {
        String lexeme = lc.lexeme().replace("_", "");

        Long initialValue;
        Subtype subtype;
        try {
            initialValue = Long.parseLong(lexeme);
            subtype = getRange(initialValue);
        } catch (NumberFormatException e) {
            initialValue = null;
            subtype = Subtype.UNKNOWN;
        }

        if (subtype == Subtype.UNKNOWN) {
            lc.diagnosticsHandler().add(new IntegerOutOfRange(lc.line(), lexeme));

            // Error Correction
            boolean isNegative = lexeme.startsWith("-");
            initialValue = isNegative ? MIN_LINT : MAX_LINT;
            lexeme = Long.toString(initialValue);
            subtype = Subtype.LINT;
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

    private Subtype getRange(long value) {
        if (value >= MIN_SINT && value <= MAX_SINT) return Subtype.SINT;
        if (value >= MIN_INT  && value <= MAX_INT)  return Subtype.INT;
        if (value >= MIN_DINT && value <= MAX_DINT) return Subtype.DINT;
        return Subtype.LINT;
    }
}