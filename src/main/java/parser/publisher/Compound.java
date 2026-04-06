package parser.publisher;

import utils.enums.Source;
import java.util.List;

public class Compound implements Publisher {
    private final List<Publisher> publishers;

    public Compound(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public void publish(Source source) {
        for (Publisher publisher : publishers) {
            publisher.publish(source);
        }
    }
}
