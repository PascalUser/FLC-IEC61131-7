package utils.builders;

import utils.enums.*;

import java.util.List;

/**
 * View of the lexical attributes of a symbol.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public interface LexemeInfoView {
    /**
     * Returns the type of the symbol.
     *
     * @return the requested attribute
     */
    Type type();
    /**
     * Returns the subtype of the symbol.
     *
     * @return the requested attribute
     */
    Subtype subtype();
    /**
     * Returns the custom type name of the symbol.
     *
     * @return the requested attribute
     */
    String customType();
    /**
     * Returns the source block of the symbol.
     *
     * @return the requested attribute
     */
    Source source();
    /**
     * Returns the use of the symbol.
     *
     * @return the requested attribute
     */
    Use use();
    /**
     * Returns the inferior limits of the symbol.
     *
     * @return the requested attribute
     */
    List<String> inferiorLimits();
    /**
     * Returns the superior limits of the symbol.
     *
     * @return the requested attribute
     */
    List<String> superiorLimits();
    /**
     * Returns the parameters of the symbol.
     *
     * @return the requested attribute
     */
    List<String> parameters();
    /**
     * Returns the initial value of the symbol.
     *
     * @return the requested attribute
     */
    Object initialValue();
}
