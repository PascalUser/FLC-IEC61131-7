package integration.lexer.parser.symboltable.builders;

import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for IEC 61131-7 source code snippets used in integration tests.
 */
public final class SourceCodeBuilder {

    private final List<String> typeDeclarations = new ArrayList<>();
    private final List<FunctionBlockBuilder> functionBlocks = new ArrayList<>();

    private SourceCodeBuilder() {}

    public static SourceCodeBuilder program() {
        return new SourceCodeBuilder();
    }

    public SourceCodeBuilder withType(String typeName, String typeDefinition) {
        typeDeclarations.add("    " + typeName + " : " + typeDefinition + ";");
        return this;
    }

    public SourceCodeBuilder withType(String typeName, String... lines) {
        StringBuilder sb = new StringBuilder();
        sb.append(typeName).append(" :\n");
        for (String line : lines) {
            sb.append("        ").append(line).append("\n");
        }
        typeDeclarations.add(sb.toString().trim());
        return this;
    }

    public FunctionBlockBuilder withFunctionBlock(String name) {
        FunctionBlockBuilder fb = new FunctionBlockBuilder(name);
        functionBlocks.add(fb);
        return fb;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();

        if (!typeDeclarations.isEmpty()) {
            sb.append("TYPE\n");
            for (String type : typeDeclarations) {
                sb.append(type).append("\n");
            }
            sb.append("END_TYPE\n");
        }

        for (FunctionBlockBuilder fb : functionBlocks) {
            sb.append(fb.build());
        }

        return sb.toString();
    }

    public static final class FunctionBlockBuilder {
        private final String name;
        private final List<VarBlockBuilder> varBlocks = new ArrayList<>();

        private FunctionBlockBuilder(String name) {
            this.name = name;
        }

        public VarBlockBuilder withVarBlock(VarBlockType type) {
            VarBlockBuilder vb = new VarBlockBuilder(type);
            varBlocks.add(vb);
            return vb;
        }

        public VarBlockBuilder withVarBlock(String type) {
            return withVarBlock(VarBlockType.fromString(type));
        }

        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append("FUNCTION_BLOCK ").append(name).append("\n");

            for (VarBlockBuilder vb : varBlocks) {
                sb.append(vb.build());
            }

            sb.append("END_FUNCTION_BLOCK\n");
            return sb.toString();
        }
    }

    public static final class VarBlockBuilder {
        private final VarBlockType type;
        private final List<String> variables = new ArrayList<>();

        private VarBlockBuilder(VarBlockType type) {
            this.type = type;
        }

        public VarBlockBuilder withVariable(String name, String typeName) {
            variables.add("    " + name + " : " + typeName + ";");
            return this;
        }

        public VarBlockBuilder withVariable(String name, String typeName, String initialValue) {
            variables.add("    " + name + " : " + typeName + " := " + initialValue + ";");
            return this;
        }

        public VarBlockBuilder withVariable(String declaration) {
            variables.add("    " + declaration + ";");
            return this;
        }

        public SourceCodeBuilder endBlock() {
            return null;
        }

        String build() {
            StringBuilder sb = new StringBuilder();
            sb.append(type.getKeyword()).append("\n");
            for (String var : variables) {
                sb.append(var).append("\n");
            }
            sb.append("END_VAR\n");
            return sb.toString();
        }
    }

    public enum VarBlockType {
        VAR("VAR"),
        VAR_INPUT("VAR_INPUT"),
        VAR_OUTPUT("VAR_OUTPUT"),
        VAR_IN_OUT("VAR_IN_OUT"),
        VAR_EXTERNAL("VAR_EXTERNAL"),
        VAR_GLOBAL("VAR_GLOBAL"),
        VAR_ACCESS("VAR_ACCESS"),
        VAR_CONSTANT("VAR CONSTANT");

        private final String keyword;

        VarBlockType(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        public static VarBlockType fromString(String s) {
            for (VarBlockType t : values()) {
                if (t.keyword.equalsIgnoreCase(s) || t.name().equalsIgnoreCase(s)) {
                    return t;
                }
            }
            return VAR;
        }
    }
}