package lexer;

import lexer.transformers.*;

public final class LexicalPreprocessors {
    // Time Literals
    public static final Transformer DATE_AND_TIMES  = new Nothing(null);
    public static final Transformer DAYTIMES        = new Nothing(null);
    public static final Transformer DATES           = new Nothing(null);
    public static final Transformer INTERVALS       =
            new UnderscoreRemover(
                new UpperCaseConverter(
                    new OmitLeadingZeroMagnitudes(
                        new OmitTrailingZeroMagnitudes(
                            new OmitLeadingZerosInMagnitudes(
                                new OmitTrailingZerosInMagnitudes(null)
                            )
                        )
                    )
                )
            );
    // Numeric Literals
    public static final Transformer NATURALS    = new UnderscoreRemover(new StripLeadingZeros(null));
    public static final Transformer INTEGERS    = new UnderscoreRemover(new StripLeadingZeros(null));
    public static final Transformer REALS       = new UnderscoreRemover(new StripRealZeros(null));
    public static final Transformer BINARY      = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    public static final Transformer OCTAL       = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    public static final Transformer HEXADECIMAL = new UnderscoreRemover(new StripBaseNumberLeadingZeros(null));
    // String Literals
    public static final Transformer STRINGS  = new StringHexResolver(new StringEscapeResolver(null));
    public static final Transformer WSTRINGS = new WStringHexResolver(new StringEscapeResolver(null));
    // Identifiers
    public static final Transformer IDENTIFIERS = new UpperCaseConverter(null);

}
