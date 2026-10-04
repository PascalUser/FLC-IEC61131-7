package parser.utils;

import parser.internals.ParsingContext;
import utils.LexemeInfo;

/**
 * Utility class for publishing declarations to the symbol table.
 * <p>
 * The {@code Publisher} pattern defers symbol table population until all
 * semantic attributes are configured. This class provides a static convenience
 * method for simple direct publishing from a {@link ParsingContext}.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 * <!-- TODO: Previously referenced parser.utils.Declaration and parser.utils.Compound, which no longer exist in the source tree. -->
 * @see ParsingContext
 */
public final class Publisher {

    private Publisher() {
        // Utility class - not instantiable
    }

    /**
     * Publishes all declared identifiers in the context to the symbol table.
     * <p>
     * Builds the {@link LexemeInfo} from the context's metadata builder,
     * mangles each identifier with the outer scope, and stores in the symbol table.
     * </p>
     *
     * @param ctx the parsing context containing identifiers and metadata
     */
    public static void publish(ParsingContext ctx) {
        LexemeInfo metadata = ctx.metadataBuilder().build();
        for (String identifier : ctx.declaredIdentifiers()) {
            String completeIdentifier = ctx.outerScopes().getNameMangled(identifier);
            ctx.symbolTable().put(completeIdentifier, metadata);
        }
    }
}
