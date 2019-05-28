package net.viperfish.ai.problemSolver;

import java.util.*;

public class LimitedDepthFirstSearch<S extends State> implements ProblemSolver<S> {

    private int limit;

    public LimitedDepthFirstSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public List<Action<S>> solve(S initialState, GoalTester<S> goalStates) {
        List<Action<S>> sequence = new LinkedList<>();
        if(goalStates.goalReached(initialState)) {
            return sequence;
        }
        recursive_depth_first(initialState, null, goalStates, sequence, limit);
        return sequence;
    }

    private boolean recursive_depth_first(S parent, Action<S> action, GoalTester<S> goalTester, List<Action<S>> sequence, int limit) {
        if (limit == 0) {
            return false;
        }
        for (Action<?> ac : parent.availableActions()) {
            @SuppressWarnings("unchecked")
            Action<S> a = (Action<S>) ac;
            S next = a.predict(parent);
            if (goalTester.goalReached(next)) {
                sequence.add(0, a);
                return true;
            }
            boolean success = recursive_depth_first(next, a, goalTester, sequence, limit - 1);
            if (success) {
                sequence.add(0, a);
                return true;
            }
        }
        return false;
    }

}
