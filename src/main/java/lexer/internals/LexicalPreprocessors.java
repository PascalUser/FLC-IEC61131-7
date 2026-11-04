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
 * <p>Chains:</p>
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
    /** Identity chain for date and time literals. */
    public static final Transformer DATE_AND_TIMES  = new Nothing(null);
    /** Identity chain for daytime literals. */
    public static final Transformer DAYTIMES        = new Nothing(null);
    /** Identity chain for date literals. */
    public static final Transformer DATES           = new Nothing(null);

    // Interval literal
    /** Chain that preprocesses interval literals. */
    public static final Transformer INTERVALS =
            new UnderscoreRemover(
                new UpperCaseConverter(
                    new OmitLeadingZeroMagnitudes(
                        new OmitTrailingZeroMagnitudes(
                            new OmitLeadingZerosInMagnitudes(
                                new OmitTrailingZerosInMagnitudes(null))))));

    // Discrete Numeric Literals
    /** Chain that preprocesses natural literals: removes underscores and leading zeros. */
    public static final Transformer NATURALS    = new UnderscoreRemover(new StripLeadingZeros(null));
    /** Chain that preprocesses integer literals: removes underscores and leading zeros. */
    public static final Transformer INTEGERS    = new UnderscoreRemover(new StripLeadingZeros(null));
    /** Chain that preprocesses binary literals: removes underscores and base leading zeros. */
    public static final Transformer BINARY      = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    /** Chain that preprocesses octal literals: removes underscores and base leading zeros. */
    public static final Transformer OCTAL       = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    /** Chain that preprocesses hexadecimal literals: removes underscores and base leading zeros. */
    public static final Transformer HEXADECIMAL = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));

    // Real Literals
    /** Chain that preprocesses real literals. */
    public static final Transformer REALS       =
            new UnderscoreRemover(
                    new StripTrailingZeros(
                            new StripLeadingZeros(null)
                    )
            );

    // String Literals
    /** Chain that preprocesses single-quoted string literals: resolves hex and standard escapes. */
    public static final Transformer STRINGS  = new StringHexResolver(new StringEscapeResolver(null));
    /** Chain that preprocesses double-quoted wide string literals: resolves hex and standard escapes. */
    public static final Transformer WSTRINGS = new WStringHexResolver(new StringEscapeResolver(null));

    // Identifiers
    /** Chain that uppercases identifiers. */
    public static final Transformer IDENTIFIERS = new UpperCaseConverter(null);

}
