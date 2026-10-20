package parser.utils;

import java.util.List;

public final class ParsingRepeatedArrayContext {
    private final int maxValue;
    private final List<PathInitializationInfo> pathsInfo;

    public ParsingRepeatedArrayContext(int maxValue, List<PathInitializationInfo> pathsInfo) {
        this.maxValue = maxValue;
        this.pathsInfo = pathsInfo;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public List<PathInitializationInfo> getPathsInfo() {
        return pathsInfo;
    }
}
