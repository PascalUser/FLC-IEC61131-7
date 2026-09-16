package utils;

import utils.enums.*;

import java.util.List;

/**
 * Holds semantic information for a lexeme in the symbol table.
 * <p>
 * This class stores all attributes associated with an identifier, literal,
 * or symbol encountered during lexical and syntactic analysis.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class LexemeInfo {
    /** The general type classification (simple, array, struct, etc.). */
    public Type type;

    /** The specific subtype (INT, REAL, BOOL, CUSTOM, etc.). */
    public Subtype subtype;

    /** Name of the custom type if {@code subtype == CUSTOM}. */
    public String customType;

    /** Usage context: variable, literal, function, rule, etc. */
    public Use use;

    /** Source block: IN, OUT, FUZZIFY, DEFUZZIFY, etc. */
    public Source source;

    /** Lower bound for subrange and array types. */
    public List<String> inferiorLimit;

    /** Upper bound for subrange and array  types. */
    public List<String> superiorLimit;

    /** Parameter list for function blocks. */
    public List<String> parameters;

    /** Initial lexeme assigned at declaration. */
    public Object initialValue;

    /**
     * Constructs a new LexemeInfo with all attributes.
     *
     * @param type           general type classification
     * @param subtype        specific subtype
     * @param customType     custom type name (or null)
     * @param use            usage context
     * @param source         source block
     * @param inferiorLimit  lower bounds for subranges and arrays
     * @param superiorLimit  upper bounds for subranges and arrays
     * @param parameters     function block parameters
     * @param initialValue   initial lexeme
     */
    public LexemeInfo(
        Type type,
        Subtype subtype,
        String customType,
        Use use,
        Source source,
        List<String> inferiorLimit,
        List<String> superiorLimit,
        List<String> parameters,
        Object initialValue
    ) {
        this.type = type;
        this.subtype = subtype;
        this.customType = customType;
        this.use = use;
        this.source = source;
        this.inferiorLimit = inferiorLimit;
        this.superiorLimit = superiorLimit;
        this.parameters = parameters;
        this.initialValue = initialValue;
    }
}