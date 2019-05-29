package net.viperfish.ai.problemSolver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        recursive_depth_first(initialState, goalStates, sequence, new HashSet<>(), limit);
        return sequence;
    }

    private boolean recursive_depth_first(S parent, GoalTester<S> goalTester, List<Action<S>> sequence, Set<S> current, int limit) {
        if (limit == 0) {
            return false;
        }
        current.add(parent);
        for (Action<?> ac : parent.availableActions()) {
            @SuppressWarnings("unchecked")
            Action<S> a = (Action<S>) ac;
            S next = a.predict(parent);
            if (goalTester.goalReached(next)) {
                sequence.add(0, a);
                return true;
            }
            if (current.contains(next)) {
                continue;
            }
            current.add(next);
            boolean success = recursive_depth_first(next, goalTester, sequence, current, limit - 1);
            current.remove(next);
            if (success) {
                sequence.add(0, a);
                return true;
            }
        }
        return false;
    }

}
