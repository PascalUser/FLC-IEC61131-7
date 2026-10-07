package utils;

/**
 * Legacy information holder for fuzzy variable metadata.
 *
 * @author Luca Serramone
 * @version 1.0
 * @since 1.0
 * @deprecated Use {@link LexemeInfo} for semantic symbol attributes; keep token
 *             codes in the lexer instead of storing them in the symbol table.
 */
public class LucaInfo {
    /** Legacy token code. */
    public int tokenNumber;
    /** Legacy token type description. */
    public String tokenType;

    /** Role assigned to the identifier. */
    public String idRole = "-";
    /** Type assigned to the variable. */
    public String varType = "-";
    /** Fuzzy type assigned to the variable. */
    public String fuzzType = "-";

    /** Position of the fuzzy set. */
    public int fuzzSetPosition = 0;
    /** Column occupied by the variable. */
    public int varColumn = 0;
    /** Size of the variable. */
    public int varSize = 0;

    /** Whether a set has been declared. */
    public boolean setDeclared = false;
    /** Whether the variable has been declared. */
    public boolean varDeclared = false;
    /** Whether the fuzzy set has been declared. */
    public boolean fuzzDeclared = false;
    /** Whether the variable is used in a rule. */
    public boolean useInRule = false;
    /** Whether the variable has been declared for defuzzification. */
    public boolean defuzzDeclared = false;
    /** Whether the fuzzy set is a singleton. */
    public boolean isSingleton = false;

    /**
     * Creates a legacy holder for the given token.
     *
     * @param tokenId the token code
     * @param tokenType the token type description
     */
    public LucaInfo(int tokenId, String tokenType) {
        this.tokenNumber = tokenId;
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "<" + tokenType + ", " + idRole + ", " + varType + ", " + fuzzType + ", " + fuzzSetPosition + ", " + varSize + ">";
    }
}