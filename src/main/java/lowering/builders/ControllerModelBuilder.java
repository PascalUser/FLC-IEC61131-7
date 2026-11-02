package lowering.builders;

import codegen.ControllerModel;
import lowering.utils.ControllerModelState;


public class ControllerModelBuilder implements ControllerModelSchema {
    private final ControllerModelState state = new ControllerModelState();

    public ControllerModelBuilder bitWidth(int newBitWidth) {
        state.bitWidth = newBitWidth;
        return this;
    }

    public int bitWidth() {
        return state.bitWidth;
    }

    public ControllerModel build() {
        return new ControllerModel(state);
    }
}
