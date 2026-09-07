package parser.utils;

public final class NameMangler {
    private static final StringBuilder PREFIX = new StringBuilder();

    public static void addScope(String scope) {
        PREFIX.append(scope).append('#');
    }

    public static String popScope() {
        int length = PREFIX.length();
        if (length == 0) return "";

        int prevHash = PREFIX.lastIndexOf("#", length - 2);
        String removed = PREFIX.substring(prevHash + 1, length - 1);

        PREFIX.setLength(prevHash + 1);

        return removed;
    }

    public static String getNameMangled(String name) {
        return PREFIX + name;
    }
}
