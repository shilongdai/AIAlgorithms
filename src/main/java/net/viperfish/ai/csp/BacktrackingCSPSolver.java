package net.viperfish.ai.csp;


import java.util.*;

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
        for (Object o : orderValues(csp, nextVarName)) {
            csp = new ConstraintProblem(orig);
            Variable<Object> var = csp.getVariable(nextVarName, Object.class);
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
        TreeMap<Integer, Set<String>> buffer = new TreeMap<>();
        unassigned.removeAll(assignedVars);
        for (String i : unassigned) {
            int size = csp.getVariable(i, Object.class).getVariation().size();
            if (!buffer.containsKey(size)) {
                buffer.put(size, new HashSet<>());
            }
            buffer.get(size).add(i);
        }
        Map.Entry<Integer, Set<String>> leastValue = buffer.firstEntry();
        String next = null;
        int maxConstraint = Integer.MIN_VALUE;
        for (String i : leastValue.getValue()) {
            int inverseConst = csp.inverseConstraints(i).size();
            if (inverseConst > maxConstraint) {
                next = i;
                maxConstraint = inverseConst;
            }
        }
        return next;
    }

    private List<Object> orderValues(ConstraintProblem csp, String varName) {
        Variable<Object> var = csp.getVariable(varName, Object.class);
        TreeMap<Integer, List<Object>> ordered = new TreeMap<>();
        for (Object o : var.getVariation()) {
            var.setValue(o);
            int elimination = 0;
            for (Constraint c : csp.inverseConstraints(varName)) {
                if (!c.validate(csp)) {
                    elimination += 1;
                }
            }
            elimination *= -1;
            if (!ordered.containsKey(elimination)) {
                ordered.put(elimination, new ArrayList<>());
            }
            ordered.get(elimination).add(o);
        }
        List<Object> result = new LinkedList<>();
        for (Integer i : ordered.descendingKeySet()) {
            result.addAll(ordered.get(i));
        }
        var.setValue(null);
        return result;
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
