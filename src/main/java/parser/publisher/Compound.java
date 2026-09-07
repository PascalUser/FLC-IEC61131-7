package parser.publisher;

import utils.builders.LexemeInfoSchema;
import utils.enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite publisher that delegates to multiple child publishers.
 * <p>
 * Allows applying the same attributes (type, source, etc.) to a group
 * of declarations at once. Used for VAR_INPUT, VAR_OUTPUT, and VAR blocks
 * where multiple variables share the same source and attributes.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public class Compound implements Publisher {
    private final List<Publisher> publishers;

    /**
     * Creates a new compound publisher.
     *
     * @param publishers list of child publishers
     */
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
    public LexemeInfoSchema inferiorLimit(String inferiorLimit) {
        for (Publisher publisher : publishers){
            publisher.inferiorLimit(inferiorLimit);
        }
        return this;
    }

    @Override
    public LexemeInfoSchema superiorLimit(String superiorLimit) {
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
    public List<String> publish() {
        ArrayList<String> publishedKeys = new ArrayList<>();
        for (Publisher publisher : publishers) {
            publishedKeys.addAll(publisher.publish());
        }
        return publishedKeys;
    }
}
