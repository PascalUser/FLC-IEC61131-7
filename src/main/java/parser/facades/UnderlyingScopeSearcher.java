package parser.facades;

import utils.LexemeInfo;
import utils.SymbolTable;
import utils.enums.Subtype;

/**
 * Resolves the ultimate underlying scope for a custom type reference.
 * <p>
 * When a variable is declared with a custom type that itself references another
 * custom type (type alias chain), this utility follows the chain to find the
 * root type definition that contains the actual field/enum structure.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class UnderlyingScopeSearcher {

    private UnderlyingScopeSearcher() {
        // Utility class - not instantiable
    }

    /**
     * Follows the custom type chain to find the root definition scope.
     *
     * @param symbolTable the symbol table
     * @param scope       the starting scope (type name)
     * @return the root scope name (where fields/enums are defined)
     */
    public static String search(SymbolTable symbolTable, String scope) {
        String underlyingScope = scope;
        LexemeInfo underlyingMetadata = symbolTable.get(scope);

        while (underlyingMetadata != null && underlyingMetadata.subtype == Subtype.CUSTOM) {
            underlyingScope = underlyingMetadata.customType;
            underlyingMetadata = symbolTable.get(underlyingScope);
        }
        return underlyingScope;
    }
}
