package compiler.parser.metadata;

import compiler.parser.initializations.Factory;
import compiler.parser.initializations.Initialization;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.Collections;
import java.util.List;

import static compiler.repository.builders.Director.makeIntegerZeroConstant;

/**
 * Metadata of a string type.
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverria
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public final class StringMetadata implements LexemeInfoView {
    private final Subtype subtype;
    private final String inferiorLimit;
    private final String superiorLimit;
    private final Initialization initialValue;

    /**
     * Creates the metadata of a string type.
     *
     * @param symbolTable symbol table of the program
     * @param initSubtype STRING or WSTRING subtype
     * @param initLength maximum length of the string
     */
    public StringMetadata(SymbolTable symbolTable, Subtype initSubtype, String initLength) {
        subtype = initSubtype;
        superiorLimit = initLength;

        LexemeInfoBuilder limitMetadataBuilder = new LexemeInfoBuilder();
        inferiorLimit = makeIntegerZeroConstant(limitMetadataBuilder);
        symbolTable.putIfAbsent(inferiorLimit, limitMetadataBuilder.build());

        initialValue = Factory.createPrimitiveInitialization(symbolTable, subtype).copy();
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.SIMPLE;
    }

    /**
     * Returns the subtype of this string type.
     *
     * @return the subtype of the string type
     */
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
        return Collections.singletonList(inferiorLimit);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> superiorLimits() {
        return Collections.singletonList(superiorLimit);
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
