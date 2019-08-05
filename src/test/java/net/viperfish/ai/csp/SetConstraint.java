package net.viperfish.ai.csp;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SetConstraint that = (SetConstraint) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), val);
    }
}
