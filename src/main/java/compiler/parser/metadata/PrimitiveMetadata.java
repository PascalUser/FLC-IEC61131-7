package compiler.parser.metadata;

import compiler.parser.initializations.Factory;
import compiler.parser.initializations.leafs.DefaultInitialization;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.List;

/**
 * Metadata of a primitive type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class PrimitiveMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final DefaultInitialization initialValue;

    /**
     * Creates the metadata of a primitive type.
     *
     * @param symbolTable symbol table used to build the default initial value
     * @param subtype primitive subtype
     */
    public PrimitiveMetadata(SymbolTable symbolTable, Subtype subtype) {
        this.subtype = subtype;
        this.initialValue  = Factory.createPrimitiveInitialization(symbolTable, subtype);
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    /** {@inheritDoc} */
    @Override
    public Subtype subtype() {
        return subtype;
    }

    /** {@inheritDoc} */
    @Override
    public String customType() {
        return null;
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
    public DefaultInitialization initialValue() {
        return initialValue;
    }
}
