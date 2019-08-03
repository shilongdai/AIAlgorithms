package net.viperfish.ai.csp;

public class SetConstraint extends Constraint {

    private Object val;

    public SetConstraint(String src, Object val) {
        super(src, src, "SetVal");
        this.val = val;
    }

    @Override
    public boolean validate(ConstraintProblem csp) {
        Variable<Object> var = csp.getVariable(getSrc(), Object.class);
        return var.getValue().equals(val);
    }
}
