package utils.builders;

import utils.LexemeInfo;
import utils.enums.Subtype;
import utils.enums.Source;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Fluent interface for setting semantic attributes on a lexeme.
 * <p>
 * Defines the builder methods used by {@link LexemeInfoBuilder}
 * to configure {@link LexemeInfo} properties.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public interface LexemeInfoSchema {

    /**
     * Sets the general type classification.
     *
     * @param type the type (SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT)
     * @return this builder for chaining
     */
    LexemeInfoSchema type(Type type);

    /**
     * Sets the specific subtype.
     *
     * @param subtype the subtype (INT, REAL, BOOL, etc.)
     * @return this builder for chaining
     */
    LexemeInfoSchema subtype(Subtype subtype);

    /**
     * Sets the custom type name.
     *
     * @param customType the custom type identifier
     * @return this builder for chaining
     */
    LexemeInfoSchema customType(String customType);

    /**
     * Sets the source block.
     *
     * @param source the source (IN, OUT, FUZZIFY, DEFUZZIFY, NONE)
     * @return this builder for chaining
     */
    LexemeInfoSchema source(Source source);

    /**
     * Sets the usage context.
     *
     * @param use the use (VARIABLE, LITERAL, FUNCTION, RULE, etc.)
     * @return this builder for chaining
     */
    LexemeInfoSchema use(Use use);

    /**
     * Sets the lower bound for subrange types.
     *
     * @param inferiorLimits the lower bound as a string
     * @return this builder for chaining
     */
    LexemeInfoSchema inferiorLimits(List<String> inferiorLimits);

    /**
     * Sets the upper bound for subrange types.
     *
     * @param superiorLimits the upper bound as a string
     * @return this builder for chaining
     */
    LexemeInfoSchema superiorLimits(List<String> superiorLimits);

    /**
     * Sets the parameter list for function blocks.
     *
     * @param parameters list of parameter names
     * @return this builder for chaining
     */
    LexemeInfoSchema parameters(List<String> parameters);

    /**
     * Sets the initial lexeme.
     *
     * @param initialValue the initial lexeme object
     * @return this builder for chaining
     */
    LexemeInfoSchema initialValue(Object initialValue);
}
