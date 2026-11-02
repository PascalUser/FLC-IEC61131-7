package lowering.semantics;

import lowering.builders.ControllerModelBuilder;

public interface SemanticAnalyzer {

    boolean construct(ControllerModelBuilder controller);
}
