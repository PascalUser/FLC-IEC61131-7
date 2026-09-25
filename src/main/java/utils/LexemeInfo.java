package utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;
import java.util.Objects;

/**
 * Holds semantic information for a lexeme in the symbol table.
 * <p>
 * This class stores all attributes associated with an identifier, literal,
 * or symbol encountered during lexical and syntactic analysis.
 * </p>
 * <p>
 * Equality is defined explicitly here (value-object semantics) rather than
 * inferred externally via reflection: any object that wants to be compared
 * against a {@code LexemeInfo} does so through {@link #equals(Object)}, and
 * this class is responsible for its own contract. This means the compiler
 * (not the JVM's unspecified field-enumeration order) enforces consistency
 * whenever a field is added, renamed, or removed.
 * </p>
 * <p>
 * This class intentionally stops at {@code equals}/{@code hashCode}/{@code
 * toString} — the standard {@link Object} contract. Turning a mismatch into
 * a human-readable, test-assertion-friendly message is a testing concern,
 * not a domain one, so that logic lives in the test-support comparator
 * instead of here.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.1
 * @since 1.0
 */
@SuppressFBWarnings(
        value = {
                "EI_EXPOSE_REP2",
                "URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD",
                "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
        },
        justification = "LexemeInfo is a DTO with public fields and direct collection references by design"
)
public final class LexemeInfo {
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
    public List<String> inferiorLimits;

    /** Upper bound for subrange and array types. */
    public List<String> superiorLimits;

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
     * @param inferiorLimits  lower bounds for subranges and arrays
     * @param superiorLimits  upper bounds for subranges and arrays
     * @param parameters     function block parameters
     * @param initialValue   initial lexeme
     */
    public LexemeInfo(
            Type type,
            Subtype subtype,
            String customType,
            Use use,
            Source source,
            List<String> inferiorLimits,
            List<String> superiorLimits,
            List<String> parameters,
            Object initialValue
    ) {
        this.type = type;
        this.subtype = subtype;
        this.customType = customType;
        this.use = use;
        this.source = source;
        this.inferiorLimits = inferiorLimits;
        this.superiorLimits = superiorLimits;
        this.parameters = parameters;
        this.initialValue = initialValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LexemeInfo)) {
            return false;
        }
        LexemeInfo other = (LexemeInfo) o;
        return type == other.type
                && subtype == other.subtype
                && Objects.equals(customType, other.customType)
                && use == other.use
                && source == other.source
                && Objects.equals(inferiorLimits, other.inferiorLimits)
                && Objects.equals(superiorLimits, other.superiorLimits)
                && Objects.equals(parameters, other.parameters)
                && Objects.equals(initialValue, other.initialValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                type, subtype, customType, use, source,
                inferiorLimits, superiorLimits, parameters, initialValue
        );
    }

    @Override
    public String toString() {
        return "LexemeInfo{"
                + "type=" + type
                + ", subtype=" + subtype
                + ", customType='" + customType + '\''
                + ", use=" + use
                + ", source=" + source
                + ", inferiorLimit=" + inferiorLimits
                + ", superiorLimit=" + superiorLimits
                + ", parameters=" + parameters
                + ", initialValue=" + initialValue
                + '}';
    }
}