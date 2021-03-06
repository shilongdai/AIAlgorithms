package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Candidate;
import net.viperfish.ai.search.State;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LocalBeamSearch implements LocalSearch {

    private int limit;

    public LocalBeamSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        List<State> states = new ArrayList<>();
        initialStates.forEach(states::add);
        PriorityQueue<Candidate> queue = new PriorityQueue<>();
        int current = 0;
        while (current < limit) {
            State potential = goalReached(states, goalTester);
            if (potential != null) {
                return potential;
            }
            if (states.size() == 0) {
                return null;
            }
            for (State s : states) {
                for (Action action : s.availableActions()) {
                    State next = action.execute(s);
                    double val = objectiveFunction.evaluate(next) * -1;
                    queue.add(new Candidate(next, val));
                }
            }
            int k = states.size();
            states.clear();
            for (int i = 0; i < k; ++i) {
                Candidate best = queue.poll();
                if (best == null) {
                    break;
                }
                states.add(best.getToExpand());
            }
            current += 1;
        }
        if (queue.size() == 0) {
            return null;
        }
        return queue.peek().getToExpand();
    }

    private State goalReached(Iterable<State> states, GoalTester goalTester) {
        for (State s : states) {
            if (goalTester.goalReached(s)) {
                return s;
            }
        }
        return null;
    }
}
