package utils.builders;

import parser.facades.*;
import parser.initializations.*;
import parser.initializations.leafs.*;
import parser.initializations.nodes.*;
import parser.publishers.StructureBlock;
import parser.utils.*;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.*;

import java.util.ArrayList;
import java.util.List;

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

    public static void makePrimitive(LexemeInfoSchema schema, SymbolTable symbolTable, Subtype subtype) {
        Initialization initialValue = Factory.createPrimitiveInitialization(symbolTable, subtype);
        schema.type(Type.SIMPLE)
                .subtype(subtype)
                .initialValue(initialValue.copy());
    }

    public static void makeCustom(LexemeInfoSchema schema, SymbolTable symbolTable, String type) {
        Initialization initialValue = (Initialization) symbolTable.get(type).initialValue;
        schema.type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType(type)
                .initialValue(initialValue.copy());
    }

    public static void makeMacro(LexemeInfoSchema schema, SymbolTable symbolTable, Integer replaceValue) {
        schema.type(Type.SIMPLE)
                .subtype(Subtype.NONE)
                .use(Use.MACRO)
                .source(Source.NONE)
                .initialValue(new MacroInitialization(symbolTable, replaceValue));
    }

    public static void makeEnumerated(LexemeInfoSchema schema, List<String> associatedValues) {
        schema.type(Type.ENUMERATE)
                .subtype(Subtype.INT)
                .parameters(associatedValues)
                .initialValue(new EnumeratedInitialization(associatedValues));
    }

    public static void makeStructure(LexemeInfoSchema schema, StructureBlock structBlock) {
        List<String> structFields = new ArrayList<>();

        List<PathInitializationInfo> fieldsInfo = structBlock.getStructInitializationInfo();
        StructInitialization initialValue = new StructInitialization();
        for (PathInitializationInfo fieldInfo : fieldsInfo) {
            initialValue.put(fieldInfo.name(), fieldInfo.initialValue());
            structFields.add(fieldInfo.name());
        }
        schema.type(Type.STRUCT)
                .subtype(Subtype.NONE)
                .parameters(structFields)
                .initialValue(initialValue);
    }

    public static void makeArray(LexemeInfoSchema schema, SymbolTable symbolTable, ArrayMetadata array) {
        Initialization defaultValue = (array.subtype().equals(Subtype.CUSTOM))
            ? (Initialization) symbolTable.get(array.customType()).initialValue
            : Factory.createPrimitiveInitialization(symbolTable, array.subtype());

        List<String> inferiorLimits = new ArrayList<>();
        List<String> superiorLimits = new ArrayList<>();
        RangesParser.parse(array.ranges(), inferiorLimits, superiorLimits);

        int dimension = DimensionCalculator.calculate(inferiorLimits, superiorLimits);

        schema.type(Type.ARRAY)
                .subtype(array.subtype())
                .customType(array.customType())
                .inferiorLimits(inferiorLimits)
                .superiorLimits(superiorLimits)
                .initialValue(
                    new RepeatedInitialization(dimension, defaultValue)
                );
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
