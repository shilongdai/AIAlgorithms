package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface LocalSearch {

    State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester);

}
