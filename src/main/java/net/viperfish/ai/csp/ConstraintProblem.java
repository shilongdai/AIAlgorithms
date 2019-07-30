package net.viperfish.ai.csp;

import java.util.*;

public class ConstraintProblem {

    private Map<String, Variable<?>> variables;
    private Map<String, List<Constraint>> adjacencyList;
    private Map<String, List<Constraint>> inverseAdjacencyList;

    public ConstraintProblem() {
        this(new HashMap<>());
    }

    public ConstraintProblem(ConstraintProblem src) {
        this(src.variables, src.adjacencyList);
        for (String k : variables.keySet()) {
            Variable<Object> old = this.getVariable(k, Object.class);
            variables.put(k, new Variable<>(old.getValue(), old.getRealDomain()));
        }
    }

    public ConstraintProblem(Map<String, Variable<?>> variables) {
        this(variables, new HashMap<>());
    }

    public ConstraintProblem(Map<String, Variable<?>> variables, Map<String, List<Constraint>> constraints) {
        this(variables, constraints, invertConstraint(constraints));
    }

    private ConstraintProblem(Map<String, Variable<?>> variables, Map<String, List<Constraint>> constraints, Map<String, List<Constraint>> inverse) {
        this.variables = new HashMap<>(variables);
        this.adjacencyList = new HashMap<>(constraints);
        this.inverseAdjacencyList = new HashMap<>(inverse);
    }

    private static Map<String, List<Constraint>> invertConstraint(Map<String, List<Constraint>> normal) {
        Map<String, List<Constraint>> result = new HashMap<>();
        for (List<Constraint> constraints : normal.values()) {
            for (Constraint c : constraints) {
                if (!result.containsKey(c.getDest())) {
                    result.put(c.getDest(), new LinkedList<>());
                }
                result.get(c.getDest()).add(c);
            }
        }
        return result;
    }

    public void addVariable(String name, Variable<?> variable) {
        this.variables.put(name, variable);
    }

    public <T> Variable<T> getVariable(String name, Class<T> type) {
        Variable<?> result = variables.get(name);
        if (result == null) {
            return null;
        }
        if (result.getVariation().size() == 0) {
            return (Variable<T>) result;
        }
        if (type.isAssignableFrom(result.getVariation().iterator().next().getClass())) {
            return (Variable<T>) result;
        } else {
            throw new IllegalArgumentException(String.format("The variable %s is not of the class %s", name, type.getCanonicalName()));
        }
    }

    public Set<String> variables() {
        return new HashMap<>(this.variables).keySet();
    }

    public void addConstraint(Constraint constraint) {
        if (!adjacencyList.containsKey(constraint.getSrc())) {
            adjacencyList.put(constraint.getSrc(), new LinkedList<>());
        }
        adjacencyList.get(constraint.getSrc()).add(constraint);

        if (!inverseAdjacencyList.containsKey(constraint.getDest())) {
            inverseAdjacencyList.put(constraint.getDest(), new LinkedList<>());
        }
        inverseAdjacencyList.get(constraint.getDest()).add(constraint);
    }

    public List<Constraint> constraints(String variableName) {
        return adjacencyList.getOrDefault(variableName, new LinkedList<>());
    }

    public List<Constraint> inverseConstraints(String varName) {
        return inverseAdjacencyList.getOrDefault(varName, new LinkedList<>());
    }

    public List<String> topologicalSort() {
        Map<String, Boolean> tracker = new HashMap<>();
        List<String> result = new ArrayList<>();
        ConstraintProblem reversed = reverse();
        for (String name : this.variables.keySet()) {
            if (tracker.containsKey(name)) {
                continue;
            }
            reversePostOrder(reversed, tracker, result, name);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstraintProblem that = (ConstraintProblem) o;
        return Objects.equals(variables, that.variables) &&
                Objects.equals(adjacencyList, that.adjacencyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables, adjacencyList);
    }

    private ConstraintProblem reverse() {
        return new ConstraintProblem(this.variables, this.inverseAdjacencyList, this.adjacencyList);
    }

    private void reversePostOrder(ConstraintProblem csp, Map<String, Boolean> tracker, List<String> result, String current) {
        tracker.put(current, true);
        for (Constraint c : csp.constraints(current)) {
            if (!tracker.containsKey(c.getDest())) {
                reversePostOrder(csp, tracker, result, c.getDest());
            }
        }
        result.add(0, current);
    }
}
