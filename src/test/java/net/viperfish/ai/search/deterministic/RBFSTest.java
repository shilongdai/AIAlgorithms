package net.viperfish.ai.search.deterministic;

public class RBFSTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver getSolver() {
        return new RecursiveBestFirstSearch();
    }
}
