package parser.utils;

public final class NameMangler {
    private final StringBuilder PREFIX = new StringBuilder();

    public void addScope(String scope) {
        PREFIX.append(scope).append('#');
    }

    public String popScope() {
        int length = PREFIX.length();
        if (length == 0) return "";

        int prevHash = PREFIX.lastIndexOf("#", length - 2);
        String removed = PREFIX.substring(prevHash + 1, length - 1);

        PREFIX.setLength(prevHash + 1);

        return removed;
    }

    public String getNameMangled(String name) {
        return PREFIX + name;
    }
}
