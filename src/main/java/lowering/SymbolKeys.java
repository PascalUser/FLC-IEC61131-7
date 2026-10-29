package lowering;

import parser.metadata.PragmaMetadata;

import java.util.Locale;

/**
 * The only place that knows how the parser names the symbols of a function block in the symbol table.
 * <p>
 * The function block is {@code <FB>}, variables {@code <FB>#<VAR>}, fuzzifiers and defuzzifiers
 * {@code <FB>#<VAR>@fuzzifier} and {@code <FB>#<VAR>@defuzzifier}, terms {@code <FB>#<VAR>#<TERM>} and pragmas
 * {@code <FB>@option#<NAME>}. Names in the rule block AST are not qualified, so they are resolved here.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class SymbolKeys {
    private static final String SEPARATOR = "#";

    private final String functionBlock;

    /**
     * Creates the keys of a function block.
     *
     * @param functionBlock name of the function block as the parser stores it (e.g. {@code MAIN})
     */
    public SymbolKeys(final String functionBlock) {
        this.functionBlock = functionBlock.toUpperCase(Locale.ROOT);
    }

    /**
     * Returns the key of the function block itself, whose {@code parameters} are its inputs and outputs in
     * declaration order.
     *
     * @return its key, e.g. {@code MAIN}
     */
    public String functionBlock() {
        return functionBlock;
    }

    /**
     * Returns the key of a variable named in the source or in the AST.
     *
     * @param name variable name, e.g. {@code angle}
     * @return its key, e.g. {@code MAIN#ANGLE}
     */
    public String variable(final String name) {
        return functionBlock + SEPARATOR + name.toUpperCase(Locale.ROOT);
    }

    /**
     * Returns the key of the FUZZIFY block of a variable.
     *
     * @param variableKey key of the variable
     * @return the key of its fuzzifier
     */
    public String fuzzifier(final String variableKey) {
        return variableKey + "@fuzzifier";
    }

    /**
     * Returns the key of the DEFUZZIFY block of a variable.
     *
     * @param variableKey key of the variable
     * @return the key of its defuzzifier
     */
    public String defuzzifier(final String variableKey) {
        return variableKey + "@defuzzifier";
    }

    /**
     * Returns the key of a linguistic term of a variable.
     *
     * @param variableKey key of the variable
     * @param term        term name, as in the AST or in the {@code parameters} of the block
     * @return its key, e.g. {@code MAIN#ANGLE#NL}
     */
    public String term(final String variableKey, final String term) {
        return variableKey + SEPARATOR + term.toUpperCase(Locale.ROOT);
    }

    /**
     * Returns the key of a pragma of the OPTION blocks.
     *
     * @param name pragma name, e.g. {@code bit_width}
     * @return its key, e.g. {@code MAIN@option#BIT_WIDTH}
     */
    public String pragma(final String name) {
        return functionBlock + PragmaMetadata.SCOPE_SUFFIX + SEPARATOR + name.toUpperCase(Locale.ROOT);
    }

    /**
     * Returns the variable name without the function block, used in the generated code.
     *
     * @param variableKey key of the variable, e.g. {@code MAIN#ANGLE}
     * @return its name, e.g. {@code ANGLE}
     */
    public String shortName(final String variableKey) {
        return variableKey.substring(variableKey.indexOf(SEPARATOR) + 1);
    }
}
