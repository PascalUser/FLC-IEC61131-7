package parser.utils;

import parser.tree.Label;
import parser.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConditionParsingContext {
    private TreeNode condition;
    private Label operator;
    private final List<TreeNode> operands;

    public ConditionParsingContext() {
        condition = new TreeNode(Label.NONE, Collections.emptyList());
        operator = Label.NONE;
        operands = new ArrayList<>();
    }

    public void addOperator(Label newOperator) {
        operator = newOperator;
    }

    public void addOperand(TreeNode operand) {
        operands.add(0, operand);
        if (operands.size() == 2) {
            condition = new TreeNode(operator, new ArrayList<>(operands));
            operands.remove(0);
            operands.set(0, condition);
        }
    }

    public TreeNode getConditionNode() {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        return condition;
    }
}
