package net.viperfish.ai.search;

import net.viperfish.ai.search.deterministic.GoalTester;

public interface PlanSearch<K> {

    Plan<K> solve(State initialState, GoalTester goalTester);

}
