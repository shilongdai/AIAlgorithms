package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.deterministic.GoalTester;

public class IterativeDeepeningNondeterministicDFS implements NondeterministicSearch {

    @Override
    public Plan<NondeterministicState> search(NondeterministicState initial, GoalTester goalReached) {
        int current = 1;
        Plan<NondeterministicState> result = null;
        while (result == null) {
            NondeterministicSearch next = new LimitedNondeterministicDFS(current++);
            result = next.search(initial, goalReached);
        }
        return result;
    }
}
