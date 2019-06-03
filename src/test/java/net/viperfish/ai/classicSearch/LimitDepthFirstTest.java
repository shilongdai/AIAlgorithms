package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;

public class LimitDepthFirstTest extends ProblemSolverTest {
    @Override
    protected ProblemSolver<NQueenProblem> getSolver() {
        return new LimitedDepthFirstSearch<>(5);
    }
}
