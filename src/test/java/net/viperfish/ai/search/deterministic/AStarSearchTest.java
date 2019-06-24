package net.viperfish.ai.search.deterministic;

public class AStarSearchTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver getSolver() {
        return new AStarSearch();
    }
}
