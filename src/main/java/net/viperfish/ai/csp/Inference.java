package net.viperfish.ai.csp;

public interface Inference {

    boolean makeConsistent(ConstraintProblem csp, String variable);

}
