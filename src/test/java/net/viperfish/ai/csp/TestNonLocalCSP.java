package net.viperfish.ai.csp;

import org.junit.Test;

public abstract class TestNonLocalCSP extends TestCSP {

    @Test
    public void testSolveColorMap() {
        ConstraintProblem mapColoring = mapColoring();
        CSPSolver solver = solver();
        mapColoring = solver.solve(mapColoring);
        this.checkSolvedMap(mapColoring);
    }

    @Test
    public void testSolveZebra() {
        ConstraintProblem zebra = zebraProblem();
        CSPSolver solver = solver();
        zebra = solver.solve(zebra);
        this.checkSolvedZebra(zebra);
    }

    protected abstract CSPSolver solver();

}
