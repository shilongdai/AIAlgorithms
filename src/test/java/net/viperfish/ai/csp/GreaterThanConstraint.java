package net.viperfish.ai.csp;

public class GreaterThanConstraint extends Constraint {

    private int amount;

    public GreaterThanConstraint(String src, String dest, int amount) {
        super(src, dest, "GreaterThan");
        this.amount = amount;
    }

    @Override
    public boolean validate(ConstraintProblem csp) {
        Variable<Integer> a = csp.getVariable(getSrc(), Integer.class);
        Variable<Integer> b = csp.getVariable(getDest(), Integer.class);

        for (int i : b.getVariation()) {
            if (amount == a.getValue() - i) {
                return true;
            }
        }
        return false;
    }
}
