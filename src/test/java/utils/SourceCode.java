package utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Builds small FCL programs for parser tests: an optional TYPE section plus one FUNCTION_BLOCK named
 * {@code main} with its variable blocks.
 * <p>
 * Declarations are passed WITHOUT the trailing semicolon; the builder adds it. Blocks are emitted in the
 * order the grammar requires (VAR_INPUT, VAR_OUTPUT, VAR) regardless of the order of the calls.
 * </p>
 */
public final class SourceCode {
    private static final String FUNCTION_BLOCK_NAME = "main";

    private final List<String> types = new ArrayList<>();
    private final Map<Block, List<String>> variables = new EnumMap<>(Block.class);
    private final List<String> body = new ArrayList<>();

    private SourceCode() {}

    /**
     * Starts a program with an empty {@code main} function block.
     *
     * @return a new builder
     */
    public static SourceCode functionBlock() {
        return new SourceCode();
    }

    /**
     * Returns the symbol table key of a variable declared in {@code main}.
     *
     * @param identifier the variable name as written in the source
     * @return the mangled, upper-cased key, e.g. {@code MAIN#VAR1}
     */
    public static String key(String identifier) {
        return FUNCTION_BLOCK_NAME.toUpperCase(Locale.ROOT) + "#" + identifier.toUpperCase(Locale.ROOT);
    }

    /**
     * Adds a type declaration, e.g. {@code "day : INT (0..31)"}.
     *
     * @param declaration the declaration without trailing semicolon
     * @return this builder
     */
    public SourceCode type(String declaration) {
        types.add(declaration);
        return this;
    }

    /**
     * Adds a variable declaration to a block, e.g. {@code "x : INT := 1"}.
     *
     * @param block       the block the variable belongs to
     * @param declaration the declaration without trailing semicolon
     * @return this builder
     */
    public SourceCode var(Block block, String declaration) {
        variables.computeIfAbsent(block, b -> new ArrayList<>()).add(declaration);
        return this;
    }

    /**
     * Adds a block of the function block body (FUZZIFY, DEFUZZIFY, RULEBLOCK, OPTION), emitted after the
     * variable blocks in call order. The caller is responsible for the order the grammar requires.
     *
     * @param block the complete block, from its opening keyword to its END_ keyword
     * @return this builder
     */
    public SourceCode body(String block) {
        body.add(block);
        return this;
    }

    /**
     * Renders the program.
     *
     * @return FCL source code
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        if (!types.isEmpty()) {
            sb.append("TYPE\n");
            for (String type : types) {
                sb.append("    ").append(type).append(";\n");
            }
            sb.append("END_TYPE\n");
        }
        sb.append("FUNCTION_BLOCK ").append(FUNCTION_BLOCK_NAME).append('\n');
        for (Map.Entry<Block, List<String>> entry : variables.entrySet()) {
            sb.append(entry.getKey().name()).append('\n');
            for (String declaration : entry.getValue()) {
                sb.append("    ").append(declaration).append(";\n");
            }
            sb.append("END_VAR\n");
        }
        for (String block : body) {
            sb.append(block).append('\n');
        }
        sb.append("END_FUNCTION_BLOCK\n");
        return sb.toString();
    }
}
