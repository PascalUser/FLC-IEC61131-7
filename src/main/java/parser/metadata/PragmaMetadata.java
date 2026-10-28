package parser.metadata;

import parser.initializations.leafs.VariableInitialization;
import utils.LexemeInfo;
import utils.SymbolTable;
import utils.builders.Director;
import utils.builders.LexemeInfoBuilder;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;
import java.util.Objects;

/**
 * Symbol table entry of a {@code PRAGMA} inside an {@code OPTION} block.
 * <p>
 * IEC 61131-7 leaves the content of {@code OPTION ... END_OPTION} to the manufacturer (5.4.1). This compiler
 * accepts {@code PRAGMA name value;} and {@code PRAGMA name;}; the latter is a flag and means
 * {@code PRAGMA name TRUE;}. Each pragma is published under {@code <function block>@option#<NAME>}, e.g.
 * {@code MAIN@option#BIT_WIDTH}, with the key of its value literal as initial value.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class PragmaMetadata implements LexemeInfoView {
    /** Appended to the function block name to form the declaration scope of its pragmas. */
    public static final String SCOPE_SUFFIX = "@option";

    private final Subtype subtype;
    private final VariableInitialization initialValue;

    private PragmaMetadata(final Subtype subtype, final String valueLiteral) {
        this.subtype = subtype;
        this.initialValue = new VariableInitialization(valueLiteral);
    }

    /**
     * Pragma with an explicit value ({@code PRAGMA bit_width 8;}). Its subtype is the one the lexer gave to the
     * value literal.
     *
     * @param symbolTable  table where the lexer registered the literal
     * @param valueLiteral key of the value literal, e.g. {@code "8"}
     * @return the pragma metadata
     */
    public static PragmaMetadata valued(final SymbolTable symbolTable, final String valueLiteral) {
        Objects.requireNonNull(valueLiteral, "valueLiteral");
        LexemeInfo literal = symbolTable.get(valueLiteral);
        Subtype literalSubtype = (literal == null) ? Subtype.UNKNOWN : literal.subtype;
        return new PragmaMetadata(literalSubtype, valueLiteral);
    }

    /**
     * Pragma without value ({@code PRAGMA name;}): a BOOL flag set to {@code TRUE}. Registers the {@code TRUE}
     * literal if the program did not use it.
     *
     * @param symbolTable table where the literal is registered
     * @return the pragma metadata
     */
    public static PragmaMetadata flag(final SymbolTable symbolTable) {
        LexemeInfoBuilder literal = new LexemeInfoBuilder();
        String key = Director.makeTrueBoolean(literal);
        symbolTable.putIfAbsent(key, literal.build());
        return new PragmaMetadata(Subtype.BOOL, key);
    }

    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    @Override
    public Subtype subtype() {
        return subtype;
    }

    @Override
    public String customType() {
        return null;
    }

    @Override
    public Source source() {
        return Source.NONE;
    }

    @Override
    public Use use() {
        return Use.PRAGMA;
    }

    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    @Override
    public List<String> superiorLimits() {
        return null;
    }

    @Override
    public List<String> parameters() {
        return null;
    }

    @Override
    public VariableInitialization initialValue() {
        return initialValue;
    }
}
