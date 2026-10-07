package integration.lexer.parser.symboltable.builders;

import utils.SymbolTable;
import utils.enums.Source;
import utils.enums.Subtype;

import java.util.function.Function;

/**
 * Data carrier for parameterized integration tests.
 * Similar to PrimitiveTestData but generic for all type categories.
 */
public final class TypeTestData {

    public final String blockKeyword;
    public final Source source;
    public final String typeName;
    public final Subtype subtype;
    public final String rawInitValue;
    public final String parsedInitValue;
    public final Function<SymbolTable, Object> initFactory;

    public TypeTestData(
            String blockKeyword,
            Source source,
            String typeName,
            Subtype subtype,
            String rawInitValue,
            String parsedInitValue,
            Function<SymbolTable, Object> initFactory) {
        this.blockKeyword = blockKeyword;
        this.source = source;
        this.typeName = typeName;
        this.subtype = subtype;
        this.rawInitValue = rawInitValue;
        this.parsedInitValue = parsedInitValue;
        this.initFactory = initFactory;
    }

    public static class Builder {
        private String blockKeyword;
        private Source source;
        private String typeName;
        private Subtype subtype;
        private String rawInitValue;
        private String parsedInitValue;
        private Function<SymbolTable, Object> initFactory;

        public Builder block(String keyword, Source source) {
            this.blockKeyword = keyword;
            this.source = source;
            return this;
        }

        public Builder type(String typeName, Subtype subtype) {
            this.typeName = typeName;
            this.subtype = subtype;
            return this;
        }

        public Builder init(String raw, String parsed, Function<SymbolTable, Object> factory) {
            this.rawInitValue = raw;
            this.parsedInitValue = parsed;
            this.initFactory = factory;
            return this;
        }

        public TypeTestData build() {
            return new TypeTestData(blockKeyword, source, typeName, subtype, rawInitValue, parsedInitValue, initFactory);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}