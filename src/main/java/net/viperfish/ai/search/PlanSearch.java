package net.viperfish.ai.search;

import net.viperfish.ai.search.deterministic.GoalTester;

public interface PlanSearch {

    Plan solve(State initialState, GoalTester goalTester);

}
