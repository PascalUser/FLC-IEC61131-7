package parser.utils;

import java.util.List;

public final class ArrayParsingContext {
    private final int maxValue;
    private final List<PathInitializationInfo> pathsInfo;

    public ArrayParsingContext(int maxValue, List<PathInitializationInfo> pathsInfo) {
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
