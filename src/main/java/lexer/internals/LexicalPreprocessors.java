package lexer.internals;

import lexer.transformers.*;
import lexer.transformers.hex_resolvers.*;

/**
 * Registry of transformer chains for each lexical category.
 * <p>
 * Defines the preprocessing pipeline (Chain of Responsibility pattern) applied
 * to raw lexemes before semantic analysis. Each category has a dedicated chain
 * composed of atomic transformers.
 * </p>
 * <p>
 * Chains:
 * <ul>
 *   <li>{@code DATE_AND_TIMES}, {@code DAYTIMES}, {@code DATES}: no preprocessing</li>
 *   <li>{@code INTERVALS}: underscore removal → uppercase → magnitude zero stripping</li>
 *   <li>{@code NATURALS}, {@code INTEGERS}: underscore removal → leading zero stripping</li>
 *   <li>{@code BINARY}, {@code OCTAL}, {@code HEXADECIMAL}: underscore removal → base leading zero stripping</li>
 *   <li>{@code REALS}: underscore removal → trailing zero stripping → leading zero stripping</li>
 *   <li>{@code STRINGS}: hex escape resolution → standard escape resolution</li>
 *   <li>{@code WSTRINGS}: hex escape resolution (4 digits) → standard escape resolution</li>
 *   <li>{@code IDENTIFIERS}: uppercase conversion</li>
 * </ul>
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see lexer.transformers.Transformer
 * @see LexicalAnalyzers
 */
public final class LexicalPreprocessors {
    // Date or time Literals
    public static final Transformer DATE_AND_TIMES  = new Nothing(null);
    public static final Transformer DAYTIMES        = new Nothing(null);
    public static final Transformer DATES           = new Nothing(null);

    // Interval literal
    public static final Transformer INTERVALS       =
            new UnderscoreRemover(
                new UpperCaseConverter(
                    new OmitLeadingZeroMagnitudes(
                        new OmitTrailingZeroMagnitudes(
                            new OmitLeadingZerosInMagnitudes(
                                new OmitTrailingZerosInMagnitudes(null))))));

    // Discrete Numeric Literals
    public static final Transformer NATURALS    = new UnderscoreRemover(new StripLeadingZeros(null));
    public static final Transformer INTEGERS    = new UnderscoreRemover(new StripLeadingZeros(null));
    public static final Transformer BINARY      = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    public static final Transformer OCTAL       = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    public static final Transformer HEXADECIMAL = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));

    // Real Literals
    public static final Transformer REALS       =
            new UnderscoreRemover(
                    new StripTrailingZeros(
                            new StripLeadingZeros(null)
                    )
            );

    // String Literals
    public static final Transformer STRINGS  = new StringHexResolver(new StringEscapeResolver(null));
    public static final Transformer WSTRINGS = new WStringHexResolver(new StringEscapeResolver(null));

    // Identifiers
    public static final Transformer IDENTIFIERS = new UpperCaseConverter(null);

}
