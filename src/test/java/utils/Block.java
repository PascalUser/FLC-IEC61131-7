package utils;

import utils.enums.Source;

/**
 * Variable blocks of a FUNCTION_BLOCK, with the {@link Source} the parser assigns to their variables.
 * <p>
 * Declared in the order the grammar requires: I/O blocks first, then internal variables.
 * </p>
 */
public enum Block {
    VAR_INPUT(Source.IN),
    VAR_OUTPUT(Source.OUT),
    VAR(Source.INTERNAL);

    private final Source source;

    Block(Source initSource) {
        source = initSource;
    }

    public Source source() {
        return source;
    }
}
