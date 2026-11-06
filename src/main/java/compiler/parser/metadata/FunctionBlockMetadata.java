package compiler.parser.metadata;

import compiler.repository.builders.LexemeInfoView;
import compiler.repository.enums.Source;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import compiler.repository.enums.Use;

import java.util.ArrayList;
import java.util.List;

/**
 * Symbol table entry of the function block itself, published under its name (e.g. {@code MAIN}).
 * <p>
 * Its {@code parameters} are the keys of the {@code VAR_INPUT} and {@code VAR_OUTPUT} variables in declaration
 * order (e.g. {@code [MAIN#ANGLE, MAIN#SPEED, MAIN#FORCE]}): the interface of the function block. The symbol
 * table is a map and does not keep that order, and the code generator needs it for the signature of the
 * controller. The direction of each variable is in its own entry ({@code source} IN/OUT).
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0
 * @since 1.0
 */
public final class FunctionBlockMetadata implements LexemeInfoView {
    private final List<String> interfaceVariables;

    /**
     * Creates the entry of a function block.
     *
     * @param interfaceVariables keys of its inputs and outputs in declaration order
     */
    public FunctionBlockMetadata(final List<String> interfaceVariables) {
        this.interfaceVariables = new ArrayList<>(interfaceVariables);
    }

    /** {@inheritDoc} */
    @Override
    public Type type() {
        return Type.NONE;
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
        return Use.MAIN;
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
        return new ArrayList<>(interfaceVariables);
    }

    /** {@inheritDoc} */
    @Override
    public Object initialValue() {
        return null;
    }
}
