package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;

public class AStarSearchTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver<NQueenProblem> getSolver() {
        return new AStarSearch<>();
    }
}
