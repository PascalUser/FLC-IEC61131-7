package utils.builders;

import utils.enums.Subtype;
import utils.enums.Source;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

public interface LexemeInfoSchema {

    LexemeInfoSchema type(Type type);

    LexemeInfoSchema subtype(Subtype subtype);

    LexemeInfoSchema customType(String customType);

    LexemeInfoSchema source(Source source);

    LexemeInfoSchema use(Use use);

    LexemeInfoSchema inferiorLimit(Integer inferiorLimit);

    LexemeInfoSchema superiorLimit(Integer superiorLimit);

    LexemeInfoSchema parameters(List<String> parameters);

    LexemeInfoSchema initialValue(Object initialValue);
}
