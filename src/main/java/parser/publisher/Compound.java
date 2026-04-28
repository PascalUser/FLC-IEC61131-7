package parser.publisher;

import utils.builders.LexemeInfoSchema;
import utils.enums.*;

import java.util.List;

public class Compound implements Publisher {
    private final List<Publisher> publishers;

    public Compound(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public Compound type(Type type) {
        for (Publisher publisher : publishers) {
            publisher.type(type);
        }
        return this;
    }

    @Override
    public Compound subtype(Subtype subtype) {
        for (Publisher publisher : publishers) {
            publisher.subtype(subtype);
        }
        return this;
    }

    @Override
    public Compound customType(String customType) {
        for (Publisher publisher : publishers) {
            publisher.customType(customType);
        }
        return this;
    }

    @Override
    public Compound source(Source source){
        for (Publisher publisher : publishers){
            publisher.source(source);
        }
        return this;
    }

    @Override
    public Compound use(Use use) {
        for (Publisher publisher : publishers){
            publisher.use(use);
        }
        return this;
    }

    @Override
    public LexemeInfoSchema inferiorLimit(Integer inferiorLimit) {
        for (Publisher publisher : publishers){
            publisher.inferiorLimit(inferiorLimit);
        }
        return this;
    }

    @Override
    public LexemeInfoSchema superiorLimit(Integer superiorLimit) {
        for (Publisher publisher : publishers){
            publisher.superiorLimit(superiorLimit);
        }
        return this;
    }

    @Override
    public LexemeInfoSchema parameters(List<String> parameters) {
        for (Publisher publisher : publishers){
            publisher.parameters(parameters);
        }
        return this;
    }

    @Override
    public Compound initialValue(Object initialValue){
        for (Publisher publisher : publishers){
            publisher.initialValue(initialValue);
        }
        return this;
    }

    @Override
    public void publish() {
        for (Publisher publisher : publishers) {
            publisher.publish();
        }
    }
}
