package net.viperfish.ai.csp;


import java.util.*;

public class BacktrackingCSPSolver implements CSPSolver {

    private Inference inference;

    public BacktrackingCSPSolver(Inference inference) {
        this.inference = inference;
    }

    @Override
    public ConstraintProblem solve(ConstraintProblem problem) {
        Map<String, SortedSet<Integer>> conflictSets = new HashMap<>();
        for (String i : problem.variables()) {
            conflictSets.put(i, new TreeSet<>());
        }
        BackTrackStatus status = backtrack(problem, new HashMap<>(), conflictSets, 0);
        return status.getCsp();
    }

    private BackTrackStatus backtrack(ConstraintProblem csp, Map<String, Integer> assignedVariables, Map<String, SortedSet<Integer>> conflictSets, int depth) {
        if (assignedVariables.keySet().equals(csp.variables())) {
            return new BackTrackStatus(csp, new TreeSet<>());
        }
        ConstraintProblem orig = csp;
        String nextVarName = selectVariable(csp, assignedVariables);
        Variable<Object> var = orig.getVariable(nextVarName, Object.class);
        fillConflictSet(csp, assignedVariables, conflictSets, nextVarName);
        for (Object o : orderValues(var.getRealDomain())) {
            csp = new ConstraintProblem(orig);
            var = csp.getVariable(nextVarName, Object.class);
            var.setValue(o);
            if (consistent(csp, assignedVariables, nextVarName)) {
                if (inference.makeConsistent(csp, nextVarName)) {
                    assignedVariables.put(nextVarName, depth);
                    BackTrackStatus result = backtrack(csp, assignedVariables, conflictSets, depth + 1);
                    if (result.failed()) {
                        SortedSet<Integer> current = conflictSets.get(nextVarName);
                        current.addAll(result.getConflictSet());
                        current.remove(depth);
                        if (current.last() == depth) {
                            continue;
                        }
                    } else {
                        return result;
                    }
                }
            }
        }

        // backtrack to latest conflict
        SortedSet<Integer> conflictSet = conflictSets.get(nextVarName);
        BackTrackStatus status = new BackTrackStatus(null, conflictSet);
        conflictSets.get(nextVarName).clear();
        return status;
    }

    private String selectVariable(ConstraintProblem csp, Map<String, Integer> assignedVars) {
        Set<String> unassigned = csp.variables();
        unassigned.removeAll(assignedVars.keySet());
        return unassigned.iterator().next();
    }

    private List<Object> orderValues(Set<Object> values) {
        return new LinkedList<>(values);
    }

    private boolean consistent(ConstraintProblem csp, Map<String, Integer> assignedVar, String current) {
        for (Constraint c : csp.constraints(current)) {
            if (assignedVar.keySet().contains(c.getDest())) {
                if (!c.validate(csp)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillConflictSet(ConstraintProblem csp, Map<String, Integer> assigned, Map<String, SortedSet<Integer>> conflictSet, String current) {
        Variable<Object> currentVar = csp.getVariable(current, Object.class);
        Object orig = currentVar.getValue();
        for (Object o : currentVar.getRealDomain()) {
            currentVar.setValue(o);
            for (Constraint c : csp.constraints(current)) {
                if (assigned.keySet().contains(c.getDest())) {
                    if (!c.validate(csp)) {
                        conflictSet.get(current).add(assigned.get(c.getDest()));
                    }
                }
            }
        }
        currentVar.setValue(orig);
    }

}
