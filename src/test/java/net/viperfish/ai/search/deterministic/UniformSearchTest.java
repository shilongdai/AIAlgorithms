package net.viperfish.ai.search.deterministic;

public class UniformSearchTest extends ProblemSolverTest {

    @Override
    protected ProblemSolver getSolver() {
        return new UniformSearch();
    }
}
