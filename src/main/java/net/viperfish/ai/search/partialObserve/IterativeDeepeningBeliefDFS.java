package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.deterministic.GoalTester;

public class IterativeDeepeningBeliefDFS implements BeliefStateSearch {

    @Override
    public Plan<Precept> solve(BeliefState initial, GoalTester goalTester) {
        int current = 1;
        Plan<Precept> result = null;
        while (result == null) {
            BeliefStateSearch next = new LimitedBeliefStateDFS(current++);
            result = next.solve(initial, goalTester);
        }
        return result;
    }


}
