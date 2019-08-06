package net.viperfish.ai.csp;

import java.util.Set;

public class MaxDegreeHeuristic implements VariableHeuristic {
    @Override
    public double evaluate(ConstraintProblem csp, Set<String> assigned, String varname) {
        return csp.inverseConstraints(varname).size() * -1;
    }
}
