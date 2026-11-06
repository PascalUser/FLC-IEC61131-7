package compiler.lexer.semantics.numbers;

import compiler.lexer.semantics.SemanticAnalyzer;
import compiler.parser.Parser;
import compiler.repository.diagnostics.Warning;
import compiler.repository.builders.Director;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.diagnostics.Diagnostic;
import compiler.repository.enums.Subtype;

/**
 * Abstract base class for numeric literal semantic analyzers.
 * <p>
 * Provides common logic for parsing numeric literals, determining their
 * subtype based on value range, registering them in the symbol table,
 * and handling out-of-range values through fallback correction with warnings.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public abstract class NumbersAnalyzer implements SemanticAnalyzer {

    /**
     * Parsed numeric value with its determined subtype.
     */
    protected static final class ParsedValue {
        /** Raw lexeme that was analyzed. */
        public final String lexeme;
        /** Subtype determined for the value. */
        public final Subtype subtype;
        /** Numeric value of the literal. */
        public final Object value;

        /**
         * Creates a parsed numeric value.
         *
         * @param lexeme raw lexeme that was analyzed
         * @param subtype subtype determined for the value
         * @param value numeric value of the literal
         */
        public ParsedValue(String lexeme, Subtype subtype, Object value) {
            this.lexeme = lexeme;
            this.subtype = subtype;
            this.value = value;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Result analyze(LexicalContext ctx) {
        String lexeme = ctx.preprocessedLexeme;
        
        // Current lexeme is parsed to determine it's subtype and value
        ParsedValue parsed = this.parse(lexeme);

        // If parsed subtype is unknown then the lexeme is invalid and needs correction.
        if (parsed.subtype == Subtype.UNKNOWN) {
            ctx.diagnosticsHandler.add(
                this.createDiagnostic(ctx.line, lexeme)
            );
            parsed = this.fallback(lexeme);
        }

        // The lexeme's metadata is built and published to the symbol table
        LexemeInfoBuilder builder = new LexemeInfoBuilder();
        Director.makeLiteral(builder);
        ctx.symbolTable.putIfAbsent(parsed.lexeme, builder
                .subtype(parsed.subtype)
                .initialValue(parsed.value)
                .build()
        );
        return new Result(parsed.lexeme, Parser.Lexer.NUMERIC_LITERAL);
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
     * Creates a warning diagnostic for an out-of-range numeric literal.
     *
     * @param line   source line number
     * @param lexeme the invalid lexeme
     * @return warning diagnostic instance (extends {@link Warning})
     */
    protected abstract Diagnostic createDiagnostic(int line, String lexeme);
}
