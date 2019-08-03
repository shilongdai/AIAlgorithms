package net.viperfish.ai.csp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TestLocalCSP extends TestCSP {

    @Test
    public void testSolveColorMap() {
        ConstraintProblem mapColoring = mapColoring();
        mapColoring = randomVariables(mapColoring);
        CSPSolver solver = solver();
        mapColoring = solver.solve(mapColoring);
        this.checkSolvedMap(mapColoring);
    }

    @Test
    public void testSolveZebra() {
        ConstraintProblem zebra = zebraProblem();
        zebra.addConstraint(new SetConstraint("Nigerian", 0));
        zebra.addConstraint(new SetConstraint("Milk", 2));
        zebra = randomVariables(zebra);
        CSPSolver solver = solver();
        zebra = solver.solve(zebra);
        this.checkSolvedZebra(zebra);
    }

    protected abstract CSPSolver solver();

    private ConstraintProblem randomVariables(ConstraintProblem csp) {
        Random rand = new Random();
        csp = new ConstraintProblem(csp);
        for (String i : csp.variables()) {
            Variable<Object> var = csp.getVariable(i, Object.class);
            List<Object> domain = new ArrayList<>(var.getRealDomain());
            Object val = domain.get(rand.nextInt(domain.size()));
            var.setValue(val);
        }
        return csp;
    }

}
