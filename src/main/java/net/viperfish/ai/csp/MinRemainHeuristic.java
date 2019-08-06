package net.viperfish.ai.csp;

import java.util.Set;

public class MinRemainHeuristic implements VariableHeuristic {
    @Override
    public double evaluate(ConstraintProblem csp, Set<String> assigned, String varname) {
        Variable<Object> var = csp.getVariable(varname, Object.class);
        return var.getVariation().size();
    }
}
