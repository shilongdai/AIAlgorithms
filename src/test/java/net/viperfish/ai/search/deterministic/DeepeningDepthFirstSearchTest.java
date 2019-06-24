package net.viperfish.ai.search.deterministic;

public class DeepeningDepthFirstSearchTest extends ProblemSolverTest {
    @Override
    protected ProblemSolver getSolver() {
        return new DeepeningDepthFirstSearch();
    }
}
