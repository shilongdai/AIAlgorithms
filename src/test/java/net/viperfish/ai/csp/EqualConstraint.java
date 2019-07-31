package net.viperfish.ai.csp;

public class EqualConstraint extends Constraint {

    public EqualConstraint(String src, String dest) {
        super(src, dest, "Equal");
    }

    @Override
    public boolean validate(ConstraintProblem csp) {
        Variable<Object> a = csp.getVariable(getSrc(), Object.class);
        Variable<Object> b = csp.getVariable(getDest(), Object.class);
        for (Object o : b.getVariation()) {
            if (o.equals(a.getValue())) {
                return true;
            }
        }
        return false;
    }
}
