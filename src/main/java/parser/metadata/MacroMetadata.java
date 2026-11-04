package parser.metadata;

import parser.initializations.leafs.MacroInitialization;
import utils.SymbolTable;
import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

import java.util.List;

/**
 * Metadata of a macro.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class MacroMetadata implements LexemeInfoView {
    private final MacroInitialization initialValue;

    /**
     * Creates the metadata of a macro.
     *
     * @param symbolTable symbol table used to resolve the replacement value
     * @param replaceValue value that replaces the macro
     */
    public MacroMetadata(SymbolTable symbolTable, Integer replaceValue) {
        initialValue = new MacroInitialization(symbolTable, replaceValue);
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return Subtype.NONE;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.NONE;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.MACRO;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> inferiorLimits() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> parameters() {
        return null;
    }

    /**
     * Returns the initialization of this macro.
     *
     * @return the initialization of the macro
     */
    public MacroInitialization initialValue() {
        return initialValue;
    }
}
