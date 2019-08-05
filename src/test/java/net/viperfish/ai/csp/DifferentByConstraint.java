package net.viperfish.ai.csp;

import java.util.Objects;

public class DifferentByConstraint extends Constraint {

    private int amount;

    public DifferentByConstraint(String src, String dest, int amount) {
        super(src, dest, "DifferentBy");
        this.amount = amount;
    }

    @Override
    public boolean validate(ConstraintProblem csp) {
        Variable<Integer> a = csp.getVariable(getSrc(), Integer.class);
        Variable<Integer> b = csp.getVariable(getDest(), Integer.class);

        for (int i : b.getVariation()) {
            if (Math.abs(i - a.getValue()) == amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DifferentByConstraint that = (DifferentByConstraint) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
    }
}
