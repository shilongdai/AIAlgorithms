package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

public class SteepAscentHillClimbSearch<S extends State> extends HillClimbSearch<S> {

    private int sideLimit;

    public SteepAscentHillClimbSearch(int limit) {
        this.sideLimit = limit;
    }

    @Override
    public S solve(S initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        S current = initialStates;
        int limit = sideLimit;
        double val = objectiveFunction.evaluate(current);
        while (true) {
            if (goalTester.goalReached(current)) {
                return current;
            }
            S best = bestNeighbor(current, objectiveFunction);
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

    private S bestNeighbor(S current, ObjectiveFunction<S> objectiveFunction) {
        S best = current;
        double val = objectiveFunction.evaluate(best);
        for (Action<?> a : current.availableActions()) {
            @SuppressWarnings("unchecked")
            Action<S> action = (Action<S>) a;
            S next = action.predict(current);
            double nextVal = objectiveFunction.evaluate(next);
            if (nextVal >= val) {
                best = next;
                val = nextVal;
            }
        }
        return best;
    }

}
