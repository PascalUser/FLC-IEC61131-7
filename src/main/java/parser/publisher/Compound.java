package parser.publisher;

import utils.enums.ElementaryType;
import utils.enums.Source;
import utils.enums.Use;

import java.util.List;

public class Compound implements Publisher {
    private final List<Publisher> publishers;

    public Compound(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Compound type(ElementaryType type) {
        for (Publisher publisher : publishers) {
            publisher.type(type);
        }
        return this;
    }

    public Compound customType(String customType) {
        for (Publisher publisher : publishers) {
            publisher.customType(customType);
        }
        return this;
    }

    public Compound source(Source source){
        for (Publisher publisher : publishers){
            publisher.source(source);
        }
        return this;
    }

    public Compound use(Use use) {
        for (Publisher publisher : publishers){
            publisher.use(use);
        }
        return this;
    }

    public Compound value(Object value){
        for (Publisher publisher : publishers){
            publisher.value(value);
        }
        return this;
    }

    public void publish() {
        for (Publisher publisher : publishers) {
            publisher.publish();
        }
    }
}
