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
    Type type();
    Subtype subtype();
    String customType();
    Source source();
    Use use();
    List<String> inferiorLimits();
    List<String> superiorLimits();
    List<String> parameters();
    Object initialValue();
}
