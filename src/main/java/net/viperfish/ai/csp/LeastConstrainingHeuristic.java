package net.viperfish.ai.csp;

public class LeastConstrainingHeuristic implements ValueHeuristic {

    @Override
    public double evaluate(ConstraintProblem csp, String varname, Object value) {
        Variable<Object> var = csp.getVariable(varname, Object.class);
        Object origTarget = var.getValue();
        int elimination = 0;
        var.setValue(value);
        for (Constraint c : csp.inverseConstraints(varname)) {
            Variable<Object> other = csp.getVariable(c.getSrc(), Object.class);
            Object orig = other.getValue();
            for (Object a : other.getVariation()) {
                other.setValue(a);
                if (!c.validate(csp)) {
                    elimination += 1;
                }
            }
            other.setValue(orig);
        }
        var.setValue(origTarget);
        return elimination;
    }
}
