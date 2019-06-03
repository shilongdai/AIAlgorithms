package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;

public class UniformSearchTest extends ProblemSolverTest {

    @Override
    protected ProblemSolver<NQueenProblem> getSolver() {
        return new UniformSearch<>();
    }
}
