package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LimitedDepthFirstSearch implements ProblemSolver {

    private int limit;

    public LimitedDepthFirstSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public List<Action> solve(State initialState, GoalTester goalStates) {
        List<Action> sequence = new LinkedList<>();
        if(goalStates.goalReached(initialState)) {
            return sequence;
        }
        recursive_depth_first(initialState, goalStates, sequence, new HashSet<>(), limit);
        return sequence;
    }

    private boolean recursive_depth_first(State parent, GoalTester goalTester, List<Action> sequence, Set<State> current, int limit) {
        if (limit == 0) {
            return false;
        }
        current.add(parent);
        for (Action a : parent.availableActions()) {
            State next = a.predict(parent);
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
