package utils.builders;

import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

public final class Director {
    public static void makeLiteral(LexemeInfoSchema schema) {
        schema.type(Type.SIMPLE)
                .use(Use.LITERAL)
                .source(Source.NONE);
    }

    public static String makeDefaultReal(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.REAL).initialValue(0D);
        return "0.0";
    }

    public static String makeDefaultBoolean(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.BOOL).initialValue(false);
        return "FALSE";
    }
}
