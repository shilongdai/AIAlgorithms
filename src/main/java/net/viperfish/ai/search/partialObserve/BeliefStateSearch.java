package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.deterministic.GoalTester;

public interface BeliefStateSearch {

    Plan<Precept> solve(BeliefState initial, GoalTester goalTester);

}
