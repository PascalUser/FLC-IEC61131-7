package lexer.semantics;

import parser.Parser;
import utils.builders.LexemeInfoBuilder;
import utils.diagnostics.Diagnostic;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

/**
 * Abstract base class for numeric literal semantic analyzers.
 * <p>
 * Provides common logic for parsing numeric literals, determining their
 * subtype based on value range, registering them in the symbol table,
 * and handling out-of-range errors through error correction.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class NumericAnalyzer implements SemanticAnalyzer {

    /**
     * Parsed numeric value with its determined subtype.
     */
    protected static final class ParsedValue {
        private final String lexeme;
        private final Subtype subtype;
        private final Object value;

        ParsedValue(String lexeme, Subtype subtype, Object value) {
            this.lexeme = lexeme;
            this.subtype = subtype;
            this.value = value;
        }

        public String lexeme() {
            return lexeme;
        }

        public Subtype subtype() {
            return subtype;
        }

        public Object value() {
            return value;
        }
    }

    @Override
    public Result analyze(LexicalContext currentContext) {
        String currentLexeme = currentContext.lexeme();

        // Current lexeme is parsed to determine it's subtype and value
        ParsedValue parsed = this.parse(currentLexeme);

        // If parsed subtype is unknown then the lexeme is invalid and needs correction.
        if (parsed.subtype() == Subtype.UNKNOWN) {
            currentContext.diagnosticsHandler().add(
                this.createDiagnostic(currentContext.line(), currentLexeme)
            );
            parsed = this.fallback(currentLexeme);
        }

        // The lexeme's metadata is built and published to the symbol table
        if (currentContext.symbolTable().get(parsed.lexeme()) == null) {
            currentContext.symbolTable().put(
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

    /**
     * Parses the lexeme and determines its subtype and value.
     *
     * @param lexeme the raw lexeme text
     * @return parsed value with subtype and numeric value
     */
    protected abstract ParsedValue parse(String lexeme);

    /**
     * Provides a corrected fallback value for invalid lexemes.
     *
     * @param lexeme the invalid lexeme
     * @return corrected parsed value
     */
    protected abstract ParsedValue fallback(String lexeme);

    /**
     * Creates a diagnostic for an out-of-range or invalid numeric literal.
     *
     * @param line   source line number
     * @param lexeme the invalid lexeme
     * @return diagnostic instance
     */
    protected abstract Diagnostic createDiagnostic(int line, String lexeme);
}