package net.viperfish.ai.problemSolver;

public class LimitDepthFirstTest extends ProblemSolverTest {
    @Override
    protected ProblemSolver<Simplified5Queen> getSolver() {
        return new LimitedDepthFirstSearch<>(10);
    }
}
