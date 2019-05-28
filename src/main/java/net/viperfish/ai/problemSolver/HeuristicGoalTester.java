package net.viperfish.ai.problemSolver;

public interface HeuristicGoalTester<S extends State> extends GoalTester<S> {

    public double heuristic(S state);

}
