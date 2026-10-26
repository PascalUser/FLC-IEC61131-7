package utils.builders;

import utils.enums.*;

import java.util.List;

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
