package net.viperfish.ai.csp;

import org.junit.Test;

public abstract class TestLocalCSP extends TestCSP {

    @Test
    public void testSolveColorMap() {
        ConstraintProblem mapColoring = mapColoring();
        mapColoring = LocalCSPSearchUtil.randomVariables(mapColoring);
        CSPSolver solver = solver();
        mapColoring = solver.solve(mapColoring);
        this.checkSolvedMap(mapColoring);
    }

    @Test
    public void testSolveZebra() {
        ConstraintProblem zebra = zebraProblem();
        zebra.addConstraint(new SetConstraint("Nigerian", 0));
        zebra.addConstraint(new SetConstraint("Milk", 2));
        Inference ac3 = new AC3();
        for (String i : zebra.variables()) {
            ac3.makeConsistent(zebra, i);
        }
        zebra = LocalCSPSearchUtil.randomVariables(zebra);
        CSPSolver solver = solver();
        zebra = solver.solve(zebra);
        this.checkSolvedZebra(zebra);
    }

    protected abstract CSPSolver solver();

}
