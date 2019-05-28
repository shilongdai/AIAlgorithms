package net.viperfish.ai.problemSolver;

public class UniformSearchTest extends ProblemSolverTest {

    @Override
    protected ProblemSolver<Simplified5Queen> getSolver() {
        return new UniformSearch<>();
    }
}
