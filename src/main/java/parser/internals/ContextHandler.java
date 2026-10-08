package parser.internals;

import utils.LexemeInfo;

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
     * @throws java.util.EmptyStackException if the stack is empty
     */
    public ParsingContext pop() {
        assertNotEmptyContext("remove");
        return contextStack.pop();
    }

    /**
     * Returns the current (top) parsing context without removing it.
     *
     * @return the current context
     * @throws java.util.EmptyStackException if the stack is empty
     */
    public ParsingContext current() {
        assertNotEmptyContext("get");
        return contextStack.lastElement();
    }

    public void publish() {
        assertNotEmptyContext("create publish with");

        ParsingContext ctx = this.contextStack.pop();
        LexemeInfo metadata = ctx.metadataBuilder().build();
        for (String identifier : ctx.declaredIdentifiers()) {
            String completeIdentifier = ctx.outerScopes().getNameMangled(identifier);
            ctx.symbolTable().put(completeIdentifier, metadata);
        }
    }

    public void createSubcontext() {
        assertNotEmptyContext("create subcontext with");

        ParsingContext ctx = this.contextStack.lastElement();
        LexemeInfo ctxMetadata = ctx.metadataBuilder().build();
        String ctxOuterScopes = ctx.outerScopes().getCurrentScope();

        ParsingContext subctx = new ParsingContext(ctx.symbolTable());
        // Copies the context source block
        subctx.metadataBuilder().source(ctxMetadata.source);
        // Copies the context block usage
        subctx.metadataBuilder().use(ctxMetadata.use);

        // Copies the context outerScope
        if (!ctxOuterScopes.isEmpty()) {
            subctx.outerScopes().addScope(ctxOuterScopes);
        }
        this.contextStack.add(subctx);
    }

    private void assertNotEmptyContext(String method) {
        if (contextStack.empty()) {
            throw new RuntimeException(
                "ContextHandler: cannot " + method + " the top context without the actual context"
            );
        }
    }
}
