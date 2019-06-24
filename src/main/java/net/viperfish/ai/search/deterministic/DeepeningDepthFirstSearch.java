package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.LinkedList;
import java.util.List;

public class DeepeningDepthFirstSearch implements ProblemSolver {

    @Override
    public List<Action> solve(State initialState, GoalTester goalStates) {
        List<Action> result = new LinkedList<>();
        int current = 1;
        while (result.isEmpty()) {
            LimitedDepthFirstSearch search = new LimitedDepthFirstSearch(current++);
            result = search.solve(initialState, goalStates);
        }
        return result;
    }
}
