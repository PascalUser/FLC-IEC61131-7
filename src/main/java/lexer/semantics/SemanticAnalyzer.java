package lexer.semantics;

import utils.enums.Subtype;

public interface SemanticAnalyzer {
    class Result {
        public String value;
        public int tokenNumber;

        public Result(String value, int tokenNumber) {
            this.value = value;
            this.tokenNumber = tokenNumber;
        }
    }

    Result analyze(String lexeme, Subtype subtype);
}