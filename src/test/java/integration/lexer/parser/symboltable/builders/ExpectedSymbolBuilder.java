package integration.lexer.parser.symboltable.builders;

import utils.builders.LexemeInfoBuilder;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Domain-specific wrapper around {@link LexemeInfoBuilder} for integration tests.
 * Provides fluent API matching business language.
 */
public final class ExpectedSymbolBuilder {

    private final LexemeInfoBuilder builder;

    private ExpectedSymbolBuilder() {
        this.builder = new LexemeInfoBuilder();
    }

    public static ExpectedSymbolBuilder type(String name) {
        ExpectedSymbolBuilder b = new ExpectedSymbolBuilder();
        return b;
    }

    public static ExpectedSymbolBuilder variable(String name) {
        ExpectedSymbolBuilder b = new ExpectedSymbolBuilder();
        b.builder.use(Use.VARIABLE);
        return b;
    }

    public static ExpectedSymbolBuilder field(String name) {
        ExpectedSymbolBuilder b = new ExpectedSymbolBuilder();
        b.builder.use(Use.FIELD);
        return b;
    }

    public static ExpectedSymbolBuilder macro(String name) {
        ExpectedSymbolBuilder b = new ExpectedSymbolBuilder();
        b.builder.use(Use.MACRO);
        return b;
    }

    public ExpectedSymbolBuilder simpleType(Subtype subtype) {
        builder.type(Type.SIMPLE).subtype(subtype);
        return this;
    }

    public ExpectedSymbolBuilder subrangeType(Subtype baseType, String lower, String upper) {
        builder.type(Type.SUBRANGE).subtype(baseType);
        if (lower != null) builder.inferiorLimits(Collections.singletonList(lower));
        if (upper != null) builder.superiorLimits(Collections.singletonList(upper));
        return this;
    }

    public ExpectedSymbolBuilder structType(List<String> fields) {
        builder.type(Type.STRUCT).subtype(Subtype.NONE).parameters(fields);
        return this;
    }

    public ExpectedSymbolBuilder enumerateType(List<String> enumerators) {
        builder.type(Type.ENUMERATE).subtype(Subtype.INT).parameters(enumerators);
        return this;
    }

    public ExpectedSymbolBuilder arrayType(Subtype customType, String customTypeName, List<String> lowerBounds, List<String> upperBounds) {
        builder.type(Type.ARRAY).subtype(customType).customType(customTypeName);
        if (lowerBounds != null) builder.inferiorLimits(lowerBounds);
        if (upperBounds != null) builder.superiorLimits(upperBounds);
        return this;
    }

    public ExpectedSymbolBuilder stringType(Subtype subtype, String length) {
        builder.type(Type.SIMPLE).subtype(subtype);
        if (length != null) builder.superiorLimits(Collections.singletonList(length));
        return this;
    }

    public ExpectedSymbolBuilder customType(String typeName) {
        builder.subtype(Subtype.CUSTOM).customType(typeName);
        return this;
    }

    public ExpectedSymbolBuilder source(Source source) {
        builder.source(source);
        return this;
    }

    public ExpectedSymbolBuilder internal() {
        return source(Source.INTERNAL);
    }

    public ExpectedSymbolBuilder input() {
        return source(Source.IN);
    }

    public ExpectedSymbolBuilder output() {
        return source(Source.OUT);
    }

    public ExpectedSymbolBuilder none() {
        return source(Source.NONE);
    }

    public ExpectedSymbolBuilder initializedWith(Object initialValue) {
        builder.initialValue(initialValue);
        return this;
    }

    public ExpectedSymbolBuilder defaultInitialization(Object initialValue) {
        builder.initialValue(initialValue);
        return this;
    }

    public ExpectedSymbolBuilder withInitialization(Consumer<InitializationBuilder> configurer) {
        InitializationBuilder ib = new InitializationBuilder();
        configurer.accept(ib);
        builder.initialValue(ib.build());
        return this;
    }

    public LexemeInfoBuilder build() {
        return builder;
    }

    public static final class InitializationBuilder {
        private Object value;

        public InitializationBuilder value(Object v) {
            this.value = v;
            return this;
        }

        public Object build() {
            return value;
        }
    }
}