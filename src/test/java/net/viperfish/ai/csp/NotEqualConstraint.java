package net.viperfish.ai.csp;

import java.util.Set;

public class NotEqualConstraint extends Constraint {

    public NotEqualConstraint(String src, String dest) {
        super(src, dest, "NotEqual");
    }

    @Override
    public boolean validate(ConstraintProblem csp) {
        Variable<Object> a = csp.getVariable(getSrc(), Object.class);
        Variable<Object> b = csp.getVariable(getDest(), Object.class);
        Set<Object> remainSet = b.getVariation();
        remainSet.removeAll(a.getVariation());
        return remainSet.size() != 0;
    }
}
