package parser.internals;

public final class NameMangler {
    private final StringBuilder prefix = new StringBuilder();

    public void addScope(final String scope) {
        prefix.append(scope).append('#');
    }

    public String getCurrentScope() {
        return prefix.substring(0, prefix.length() - 1);
    }

    public String popScope() {
        int length = prefix.length();
        if (length == 0) return "";

        int prevHash = prefix.lastIndexOf("#", length - 2);
        String removed = prefix.substring(prevHash + 1, length - 1);

        prefix.setLength(prevHash + 1);

        return removed;
    }

    public String getNameMangled(final String name) {
        return prefix + name;
    }
}
