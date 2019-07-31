package net.viperfish.ai.csp;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BacktrackingCSPSolver implements CSPSolver {

    private Inference inference;

    public BacktrackingCSPSolver(Inference inference) {
        this.inference = inference;
    }

    @Override
    public ConstraintProblem solve(ConstraintProblem problem) {
        Set<String> assigned = new HashSet<>();
        for (String i : problem.variables()) {
            Variable<Object> v = problem.getVariable(i, Object.class);
            if (v.getValue() != null) {
                assigned.add(i);
            }
        }
        return backtrack(problem, assigned);
    }

    private ConstraintProblem backtrack(ConstraintProblem csp, Set<String> assignedVariables) {
        if (assignedVariables.equals(csp.variables())) {
            return csp;
        }
        ConstraintProblem orig = csp;
        String nextVarName = selectVariable(csp, assignedVariables);
        Variable<Object> var = orig.getVariable(nextVarName, Object.class);
        for (Object o : orderValues(var.getRealDomain())) {
            csp = new ConstraintProblem(orig);
            var = csp.getVariable(nextVarName, Object.class);
            var.setValue(o);
            if (consistent(csp, assignedVariables, nextVarName)) {
                if (inference.makeConsistent(csp, nextVarName)) {
                    assignedVariables.add(nextVarName);
                    ConstraintProblem result = backtrack(csp, assignedVariables);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        assignedVariables.remove(nextVarName);
        return null;
    }

    private String selectVariable(ConstraintProblem csp, Set<String> assignedVars) {
        Set<String> unassigned = csp.variables();
        unassigned.removeAll(assignedVars);
        return unassigned.iterator().next();
    }

    private List<Object> orderValues(Set<Object> values) {
        return new LinkedList<>(values);
    }

    private boolean consistent(ConstraintProblem csp, Set<String> assignedVar, String current) {
        for (Constraint c : csp.constraints(current)) {
            if (assignedVar.contains(c.getDest())) {
                if (!c.validate(csp)) {
                    return false;
                }
            }
        }
        return true;
    }

}
