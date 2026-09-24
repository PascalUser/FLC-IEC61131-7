package lexer.internals;

import lexer.transformers.*;
import lexer.transformers.hex_resolvers.*;

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
