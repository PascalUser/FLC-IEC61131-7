package parser.internals;

import java.util.Stack;

public final class ContextHandler {
    private final Stack<ParsingContext> contextStack;

    public ContextHandler() {
        this.contextStack = new Stack<>();
    }

    public void add(ParsingContext ctx) {
        contextStack.add(ctx);
    }

    public ParsingContext pop() {
        return contextStack.pop();
    }

    public ParsingContext current() {
        return contextStack.lastElement();
    }
}
