package lexer;

import utils.ElementaryType;

public interface SemanticAnalyzer {
    class Result {
        public String value;
        public int tokenNumber;

        public Result(String value, int tokenNumber) {
            this.value = value;
            this.tokenNumber = tokenNumber;
        }
    }

    Result analyze(String lexeme, ElementaryType type);
}