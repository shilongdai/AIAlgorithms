package net.viperfish.ai.localSearch;

import net.viperfish.ai.Candidate;
import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LocalBeamSearch<S extends State> implements LocalSearch<S> {
    @Override
    public S solve(Iterable<S> initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        List<S> states = new ArrayList<>();
        initialStates.forEach(states::add);
        PriorityQueue<Candidate<S>> queue = new PriorityQueue<>();
        while (true) {
            S potential = goalReached(states, goalTester);
            if (potential != null) {
                return potential;
            }
            if (states.size() == 0) {
                return null;
            }
            for (S s : states) {
                for (Action<?> a : s.availableActions()) {
                    @SuppressWarnings("unchecked")
                    Action<S> action = (Action<S>) a;
                    S next = action.predict(s);
                    double val = objectiveFunction.evaluate(next) * -1;
                    queue.add(new Candidate<>(next, val));
                }
            }
            int k = states.size();
            states.clear();
            for (int i = 0; i < k; ++i) {
                Candidate<S> best = queue.poll();
                if (best == null) {
                    break;
                }
                states.add(best.getToExpand());
            }
        }
    }

    private S goalReached(Iterable<S> states, GoalTester<S> goalTester) {
        for (S s : states) {
            if (goalTester.goalReached(s)) {
                return s;
            }
        }
        return null;
    }
}
