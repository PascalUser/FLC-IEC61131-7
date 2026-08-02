package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.NaturalOutOfRange;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.math.BigInteger;

public class Naturals implements SemanticAnalyzer {
    private static final int MAX_CONSTANT_LENGTH = 20;

    private static final BigInteger MAX_USINT = BigInteger.valueOf(255);
    private static final BigInteger MAX_UINT = BigInteger.valueOf(65535);
    private static final BigInteger MAX_UDINT = new BigInteger("4294967295");
    private static final BigInteger MAX_ULINT = new BigInteger("18446744073709551615");

    public Result analyze(LexicalContext lc) {
        String lexeme = lc.lexeme()
                .replace("_", "")
                .replaceFirst("^0+(?!$)", "");

        BigInteger initialValue = (lexeme.length() <= MAX_CONSTANT_LENGTH)
                ? new BigInteger(lexeme)
                : null;

        Subtype subtype = (initialValue != null)
                ? getRange(initialValue)
                : Subtype.UNKNOWN;

        if (subtype == Subtype.UNKNOWN) {
            lc.diagnosticsHandler().add(new NaturalOutOfRange(lc.line(), lexeme));

            // Error Correction
            lexeme = MAX_ULINT.toString();
            initialValue = MAX_ULINT;
            subtype = Subtype.ULINT;
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

    private Subtype getRange(BigInteger value) {
        if (value.compareTo(MAX_USINT) <= 0) return Subtype.USINT;
        if (value.compareTo(MAX_UINT)  <= 0) return Subtype.UINT;
        if (value.compareTo(MAX_UDINT) <= 0) return Subtype.UDINT;
        if (value.compareTo(MAX_ULINT) <= 0) return Subtype.ULINT;
        return Subtype.UNKNOWN;
    }
}
