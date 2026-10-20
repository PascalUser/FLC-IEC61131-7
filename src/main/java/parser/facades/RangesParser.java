package parser.facades;

import java.util.List;

public class RangesParser {
    public static void parse(List<String> ranges, List<String> inferiorLimits, List<String> superiorLimits) {
        boolean toInferior = true;
        for (String rangeLimit : ranges) {
            if (toInferior) {
                inferiorLimits.add(rangeLimit);
            } else {
                superiorLimits.add(rangeLimit);
            }
            toInferior = !toInferior;
        }
    }
}
