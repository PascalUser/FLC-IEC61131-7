package compiler.parser.metadata;

import compiler.parser.initializations.Initialization;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.List;

/**
 * Metadata of a custom type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class CustomMetadata implements LexemeInfoView {
    private final String customType;
    private final Initialization initialValue;

    /**
     * Creates the metadata of a custom type.
     *
     * @param symbolTable symbol table that contains the custom type
     * @param customType name of the custom type
     */
    public CustomMetadata(SymbolTable symbolTable, String customType) {
        this.customType = customType;
        this.initialValue = ((Initialization) symbolTable.get(customType).initialValue).copy();
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return Subtype.CUSTOM;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return customType;
    }

    /** {@inheritDoc} */
    @Override
    public Source source() {
        return Source.UNKNOWN;
    }

    /** {@inheritDoc} */
    @Override
    public Use use() {
        return Use.UNKNOWN;
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

    /** {@inheritDoc} */
    @Override
    public Initialization initialValue() {
        return initialValue;
    }
}
