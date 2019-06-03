package net.viperfish.ai.classicSearch;

public interface HeuristicGoalTester<S extends State> extends GoalTester<S> {

    double heuristic(S state);

}
