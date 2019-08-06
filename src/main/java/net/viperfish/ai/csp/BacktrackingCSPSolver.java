package net.viperfish.ai.csp;

import java.util.*;

public class BacktrackingCSPSolver implements CSPSolver {

    private Inference inference;
    private List<VariableHeuristic> varHeuristics;
    private List<ValueHeuristic> valHeuristics;
    private double delta;

    public BacktrackingCSPSolver() {
        this(new AC3());
    }

    public BacktrackingCSPSolver(Inference inference) {
        this(inference, new ArrayList<>());
    }

    public BacktrackingCSPSolver(Inference inference, List<VariableHeuristic> varHeuristics) {
        this(inference, varHeuristics, new ArrayList<>());
    }

    public BacktrackingCSPSolver(Inference inference, List<VariableHeuristic> varHeuristics, List<ValueHeuristic> valHeuristics) {
        this(inference, varHeuristics, valHeuristics, Double.MIN_NORMAL * 4);
    }

    public BacktrackingCSPSolver(Inference inference, List<VariableHeuristic> varHeuristics, List<ValueHeuristic> valHeuristics, double delta) {
        this.inference = inference;
        this.varHeuristics = new ArrayList<>(varHeuristics);
        this.valHeuristics = new ArrayList<>(valHeuristics);
        this.delta = delta;
    }

    public void addVarHeuristic(VariableHeuristic h) {
        this.varHeuristics.add(h);
    }

    public void removeVarHeuristic(VariableHeuristic h) {
        this.varHeuristics.remove(h);
    }

    public void addValHeuristic(ValueHeuristic h) {
        valHeuristics.add(h);
    }

    public void removeValHeuristic(ValueHeuristic h) {
        this.valHeuristics.remove(h);
    }

    public void clearValHeuristics() {
        this.valHeuristics.clear();
    }

    public void clearVarHeuristics() {
        varHeuristics.clear();
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
        unassigned.removeAll(assignedVars);
        TreeMap<Double, Set<String>> buffer = new TreeMap<>();
        for (VariableHeuristic h : this.varHeuristics) {
            for (String i : unassigned) {
                double estimate = h.evaluate(csp, assignedVars, i);
                if (!buffer.containsKey(estimate)) {
                    buffer.put(estimate, new HashSet<>());
                }
                buffer.get(estimate).add(i);
            }
            Double best = buffer.firstKey();
            for (Double d : buffer.navigableKeySet()) {
                if (Math.abs(d - best) > delta) {
                    unassigned.removeAll(buffer.get(d));
                }
            }
            buffer.clear();
        }

        List<String> result = new ArrayList<>(unassigned);
        return result.get(new Random().nextInt(result.size()));
    }

    private List<Object> orderValues(ConstraintProblem csp, String varName) {
        List<ValueOrderingCandidate> buffer = new ArrayList<>();
        List<ValueHeuristic> heuristics = this.valHeuristics;
        Variable<Object> target = csp.getVariable(varName, Object.class);
        if (heuristics.size() == 0) {
            return new ArrayList<>(target.getVariation());
        }

        // first pass through
        ValueHeuristic initial = heuristics.get(0);
        for (Object o : target.getVariation()) {
            buffer.add(new ValueOrderingCandidate(initial.evaluate(csp, varName, o), o));
        }
        Collections.sort(buffer);

        // use other heuristics to sort equal sections
        for (int i = 1; i < heuristics.size(); ++i) {
            int start = 0;
            for (int j = 1; j <= buffer.size(); ++j) {
                ValueOrderingCandidate c = buffer.get(i - 1);
                ValueOrderingCandidate startCandidate = buffer.get(start);
                if (c.getH() - startCandidate.getH() > delta) {
                    Collections.sort(buffer.subList(start, j));
                    start = j;
                }
            }
        }
        List<Object> result = new ArrayList<>();
        for (ValueOrderingCandidate c : buffer) {
            result.add(c.getValue());
        }
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

    private static class ValueOrderingCandidate implements Comparable<ValueOrderingCandidate> {

        private double h;
        private Object value;

        public ValueOrderingCandidate(double h, Object value) {
            this.h = h;
            this.value = value;
        }

        public double getH() {
            return h;
        }

        public void setH(double h) {
            this.h = h;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValueOrderingCandidate that = (ValueOrderingCandidate) o;
            return Double.compare(that.h, h) == 0 &&
                    Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(h, value);
        }

        @Override
        public int compareTo(ValueOrderingCandidate valueOrderingCandidate) {
            return Double.compare(this.h, valueOrderingCandidate.h);
        }
    }

}
