package parser.utils;

import parser.tree.ParserLabel;
import parser.tree.ParserTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConditionParsingContext {
    private ParserTreeNode condition;
    private ParserLabel operator;
    private final List<ParserTreeNode> operands;

    public ConditionParsingContext() {
        condition = new ParserTreeNode(ParserLabel.NONE, Collections.emptyList());
        operator = ParserLabel.NONE;
        operands = new ArrayList<>();
    }

    public void addOperator(ParserLabel newOperator) {
        operator = newOperator;
    }

    public void addOperand(ParserTreeNode operand) {
        operands.add(0, operand);
        if (operands.size() == 2) {
            condition = new ParserTreeNode(operator, new ArrayList<>(operands));
            operands.remove(0);
            operands.set(0, condition);
        }
    }

    public ParserTreeNode getConditionNode() {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        return condition;
    }
}
