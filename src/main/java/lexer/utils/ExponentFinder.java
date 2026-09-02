package lexer.utils;

public class ExponentFinder {

    public static int find(String lexeme) {
        int upper = lexeme.indexOf('E');
        int lower = lexeme.indexOf('e');

        if (upper == -1) {
            return lower;
        }

        if (lower == -1) {
            return upper;
        }

        return Math.min(upper, lower);
    }
}
