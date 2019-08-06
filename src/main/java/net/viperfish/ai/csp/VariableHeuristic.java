package net.viperfish.ai.csp;

import java.util.Set;

public interface VariableHeuristic {

    double evaluate(ConstraintProblem csp, Set<String> assigned, String varname);

}
