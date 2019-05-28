package net.viperfish.ai.problemSolver;

public class DeepeningDepthFirstSearchTest extends ProblemSolverTest {
    @Override
    protected ProblemSolver<Simplified5Queen> getSolver() {
        return new DeepeningDepthFirstSearch<>();
    }
}
