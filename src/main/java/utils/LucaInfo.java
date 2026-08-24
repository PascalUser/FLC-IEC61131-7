package utils;

/**
 * Legacy information holder for fuzzy variable metadata.
 *
 * @author Luca Serramone
 * @version 1.0
 * @since 1.0
 * @deprecated Consider removing - symbol table should not store token info
 */
public class LucaInfo {
    public int tokenNumber;
    public String tokenType;

    public String idRole = "-";
    public String varType = "-";
    public String fuzzType = "-";

    public int fuzzSetPosition = 0;
    public int varColumn = 0;
    public int varSize = 0;

    public boolean setDeclared = false;
    public boolean varDeclared = false;
    public boolean fuzzDeclared = false;
    public boolean useInRule = false;
    public boolean defuzzDeclared = false;
    public boolean isSingleton = false;

    public LucaInfo(int tokenId, String tokenType) {
        this.tokenNumber = tokenId;
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "<" + tokenType + ", " + idRole + ", " + varType + ", " + fuzzType + ", " + fuzzSetPosition + ", " + varSize + ">";
    }
}