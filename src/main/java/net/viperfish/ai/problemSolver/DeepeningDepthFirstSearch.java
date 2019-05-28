package net.viperfish.ai.problemSolver;

import java.util.LinkedList;
import java.util.List;

public class DeepeningDepthFirstSearch<S extends State> implements ProblemSolver<S> {
    @Override
    public List<Action<S>> solve(S initialState, GoalTester<S> goalStates) {
        List<Action<S>> result = new LinkedList<>();
        int current = 1;
        while (result.isEmpty()) {
            LimitedDepthFirstSearch<S> search = new LimitedDepthFirstSearch<>(current++);
            result = search.solve(initialState, goalStates);
        }
        return result;
    }
}
