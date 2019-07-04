package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

public class SteepAscentHillClimbSearch implements LocalSearch {

    private int sideLimit;

    public SteepAscentHillClimbSearch(int limit) {
        this.sideLimit = limit;
    }

    @Override
    public State solve(Iterable<? extends State> initialStates, ObjectiveFunction objectiveFunction, GoalTester goalTester) {
        if (!initialStates.iterator().hasNext()) {
            throw new IllegalArgumentException("The initial states cannot be empty");
        }
        State current = initialStates.iterator().next();
        int limit = sideLimit;
        double val = objectiveFunction.evaluate(current);
        while (true) {
            if (goalTester.goalReached(current)) {
                return current;
            }
            State best = bestNeighbor(current, objectiveFunction);
            double bestVal = objectiveFunction.evaluate(best);
            if (bestVal - val > Double.MIN_NORMAL) {
                current = best;
                val = bestVal;
            } else if (Math.abs(bestVal - val) < Double.MIN_NORMAL) {
                if (limit-- != 0) {
                    current = best;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    private State bestNeighbor(State current, ObjectiveFunction objectiveFunction) {
        State best = current;
        double val = objectiveFunction.evaluate(best);
        for (Action action : current.availableActions()) {
            State next = action.execute(current);
            double nextVal = objectiveFunction.evaluate(next);
            if (nextVal >= val) {
                best = next;
                val = nextVal;
            }
        }
        return best;
    }
}
