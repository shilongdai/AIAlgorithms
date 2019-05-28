package net.viperfish.ai.problemSolver;

public class AStarSearchTest extends HeuristicProblemSolverTest {


    @Override
    protected HeuristicProblemSolver<Simplified5Queen> getSolver() {
        return new AStarSearch<Simplified5Queen>();
    }
}
