package parser.metadata;

import utils.builders.LexemeInfoView;
import utils.enums.Source;
import utils.enums.Subtype;
import utils.enums.Type;
import utils.enums.Use;

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

    @Override
    public Type type() {
        return Type.NONE;
    }

    @Override
    public Subtype subtype() {
        return Subtype.NONE;
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
        return Use.FUNCTION_BLOCK;
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
        return new ArrayList<>(interfaceVariables);
    }

    @Override
    public Object initialValue() {
        return null;
    }
}
