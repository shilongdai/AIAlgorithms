package net.viperfish.ai.search.deterministic;

public class LimitDepthFirstTest extends ProblemSolverTest {
    @Override
    protected ProblemSolver getSolver() {
        return new LimitedDepthFirstSearch(5);
    }
}
