package parser.utils;

import parser.internals.ParsingContext;
import utils.LexemeInfo;

public final class Publisher {
    /**
     * Creates the metadata associated to the left identifiers using the context and publishes it into the symbol
     * table. Also, the variables names are transformed to be mangled with their outer scope.
     **/
    public static void publish(ParsingContext ctx) {
        LexemeInfo metadata = ctx.metadataBuilder().build();
        for (String identifier : ctx.declaredIdentifiers()) {
            String completeIdentifier = ctx.outerScopes().getNameMangled(identifier);
            ctx.symbolTable().put(completeIdentifier, metadata);
        }
    }
}
