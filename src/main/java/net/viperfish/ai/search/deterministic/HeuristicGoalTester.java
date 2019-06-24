package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface HeuristicGoalTester extends GoalTester {

    double heuristic(State state);

}
