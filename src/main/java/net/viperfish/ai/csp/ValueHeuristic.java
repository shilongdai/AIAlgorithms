package net.viperfish.ai.csp;

public interface ValueHeuristic {

    double evaluate(ConstraintProblem csp, String varname, Object value);

}
