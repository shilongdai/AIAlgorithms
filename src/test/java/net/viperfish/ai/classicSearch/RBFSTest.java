package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;

public class RBFSTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver<NQueenProblem> getSolver() {
        return new RecursiveBestFirstSearch<>();
    }
}
