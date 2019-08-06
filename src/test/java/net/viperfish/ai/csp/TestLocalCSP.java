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


    protected abstract CSPSolver solver();

}
