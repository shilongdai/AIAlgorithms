package net.viperfish.ai.csp;

import java.util.LinkedList;
import java.util.Queue;

public class AC3 implements Inference {
    @Override
    public boolean makeConsistent(ConstraintProblem csp, String variable) {
        Queue<Constraint> constraints = new LinkedList<>(csp.inverseConstraints(variable));
        while (!constraints.isEmpty()) {
            Constraint next = constraints.poll();
            if (revise(csp, next)) {
                Variable<?> var = csp.getVariable(next.getSrc(), Object.class);
                if (var.getVariation().size() == 0) {
                    return false;
                }
                for (Constraint toDo : csp.inverseConstraints(next.getSrc())) {
                    constraints.offer(toDo);
                }
            }
        }
        return true;
    }

    private boolean revise(ConstraintProblem csp, Constraint c) {
        boolean revised = false;
        Variable<Object> currentVar = csp.getVariable(c.getSrc(), Object.class);
        Object orig = currentVar.getValue();
        for (Object v : currentVar.getRealDomain()) {
            currentVar.setValue(v);
            if (!c.validate(csp)) {
                currentVar.eliminate(v);
                revised = true;
            }
        }
        if (currentVar.getRealDomain().contains(orig) || orig == null) {
            currentVar.setValue(orig);
        } else {
            currentVar.eliminate(currentVar.getRealDomain());
        }
        return revised;
    }
}
