package net.viperfish.ai.problemSolver;

public class RBFSTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver<Simplified5Queen> getSolver() {
        return new RecursiveBestFirstSearch<>();
    }
}
