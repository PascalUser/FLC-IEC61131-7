package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.Diagnostic;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public abstract class NumericAnalyzer implements SemanticAnalyzer {
    protected record ParsedValue(String lexeme, Subtype subtype, Object value) {}

    @Override
    public Result analyze(LexicalContext lc) {
        ParsedValue parsed = parse(lc.lexeme());

        // If unknown, the lexeme is invalid and needs correction.
        if (parsed.subtype() == Subtype.UNKNOWN) {
            lc.diagnosticsHandler().add(createDiagnostic(lc.line(), lc.lexeme()));
            parsed = fallback(lc.lexeme());
        }

        if (lc.symbolTable().get(parsed.lexeme()) == null) {
            lc.symbolTable().put(
                    parsed.lexeme(),
                    new LexemeInfoBuilder()
                            .type(Type.SIMPLE)
                            .subtype(parsed.subtype())
                            .use(Use.LITERAL)
                            .initialValue(parsed.value())
                            .build()
            );
        }
        return new Result(parsed.lexeme(), Parser.Lexer.NUMERIC_LITERAL);
    }

    protected abstract ParsedValue parse(String lexeme);
    protected abstract ParsedValue fallback(String lexeme); // Error Correction Code
    protected abstract Diagnostic  createDiagnostic(int line, String lexeme);
}
