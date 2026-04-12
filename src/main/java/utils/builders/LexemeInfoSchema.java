package utils.builders;

import utils.enums.ElementaryType;
import utils.enums.Source;
import utils.enums.Use;

public interface LexemeInfoSchema {

    LexemeInfoSchema type(ElementaryType type);

    LexemeInfoSchema customType(String customType);

    LexemeInfoSchema source(Source source);

    LexemeInfoSchema use(Use use);

    LexemeInfoSchema value(Object value);
}
