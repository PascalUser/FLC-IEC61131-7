package parser.internals;

import java.util.Stack;

/**
 * Manages the stack of parsing contexts during syntactic analysis.
 * <p>
 * The parser maintains a stack of {@link ParsingContext} objects to handle nested
 * scopes (function block, type declarations, struct fields, array initializations).
 * This handler provides LIFO operations for pushing, popping, and accessing the
 * current context.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * @see ParsingContext
 */
public final class ContextHandler {
    private final Stack<ParsingContext> contextStack;

    /**
     * Creates a new context handler with an empty stack.
     */
    public ContextHandler() {
        this.contextStack = new Stack<>();
    }

    /**
     * Pushes a parsing context onto the stack.
     *
     * @param ctx the context to push
     */
    public void add(ParsingContext ctx) {
        contextStack.add(ctx);
    }

    /**
     * Pops and returns the top parsing context.
     *
     * @return the removed context
     * @throws EmptyStackException if the stack is empty
     */
    public ParsingContext pop() {
        return contextStack.pop();
    }

    /**
     * Returns the current (top) parsing context without removing it.
     *
     * @return the current context
     * @throws EmptyStackException if the stack is empty
     */
    public ParsingContext current() {
        return contextStack.lastElement();
    }
}
