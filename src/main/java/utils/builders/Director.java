package utils.builders;

import utils.LexemeInfo;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

/**
 * Director for constructing common {@link LexemeInfo} configurations.
 * <p>
 * Provides predefined recipes for common semantic patterns (literals, defaults)
 * to ensure consistency across the parser's semantic actions.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see LexemeInfoBuilder
 * @see LexemeInfoSchema
 */
public final class Director {

    private Director() {
        // Utility class - not instantiable
    }

    /**
     * Configures the schema for a literal value.
     * <p>
     * Sets: {@code type=SIMPLE}, {@code use=LITERAL}, {@code source=NONE}.
     * </p>
     *
     * @param schema the schema to configure
     */
    public static void makeLiteral(LexemeInfoSchema schema) {
        schema.type(Type.SIMPLE)
                .use(Use.LITERAL)
                .source(Source.NONE);
    }

    public static void makeType(LexemeInfoSchema schema) {
        schema.use(Use.TYPE)
                .source(Source.NONE);
    }

    public static void makeMacro(LexemeInfoSchema schema) {
        schema.type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE);
    }

    public static String makeDefaultInteger(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.INT).initialValue(0);
        return "0";
    }

    /**
     * Configures the schema for a default REAL value and returns the literal string.
     *
     * @param schema the schema to configure
     * @return the default literal string "0.0"
     */
    public static String makeDefaultReal(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.REAL).initialValue(0D);
        return "0.0";
    }

    /**
     * Configures the schema for a default BOOL value and returns the literal string.
     *
     * @param schema the schema to configure
     * @return the default literal string "FALSE"
     */
    public static String makeDefaultBoolean(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.BOOL).initialValue(false);
        return "FALSE";
    }

    /**
     * Configures a default STRING literal and returns its source spelling.
     *
     * @param schema the schema to configure
     * @return the empty STRING literal {@code ''}
     */
    public static String makeDefaultString(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.STRING).initialValue("");
        return "''";
    }

    /**
     * Configures a default WSTRING literal and returns its source spelling.
     *
     * @param schema the schema to configure
     * @return the empty WSTRING literal {@code ""}
     */
    public static String makeDefaultWString(LexemeInfoSchema schema) {
        makeLiteral(schema);
        schema.subtype(Subtype.WSTRING).initialValue("");
        return "\"\"";
    }
}
