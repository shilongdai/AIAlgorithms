package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.PerfectPrecept;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.StateActionPair;

import java.util.HashMap;
import java.util.Map;

public class LearningRealTimeAStarSearch extends OnlineSearch {

    private Map<StateActionPair, State> result;
    private Map<State, Double> estimate;
    private State previousState;
    private Action previousAction;
    private HeuristicGoalTester goalTester;

    public LearningRealTimeAStarSearch(HeuristicGoalTester goalTester) {
        this.goalTester = goalTester;
        result = new HashMap<>();
        estimate = new HashMap<>();
        previousAction = null;
        previousState = null;
    }

    @Override
    public Action next(PerfectPrecept precept) {
        State perceived = precept.getState();
        if (goalTester.goalReached(perceived)) {
            previousState = perceived;
            return null;
        }
        if (!estimate.containsKey(perceived)) {
            estimate.put(perceived, goalTester.heuristic(perceived));
        }
        if (previousState != null) {
            this.result.put(new StateActionPair(previousState, previousAction), perceived);
            Action best = null;
            double bestCost = Double.POSITIVE_INFINITY;
            for (Action a : previousState.availableActions()) {
                if (a.cost() < bestCost) {
                    best = a;
                    bestCost = a.cost();
                }
            }
            estimate.put(previousState, cost(best, previousState, perceived));
        }
        Action result = null;
        double bestCost = Double.POSITIVE_INFINITY;
        for (Action action : perceived.availableActions()) {
            double cost = cost(action, perceived, this.result.get(new StateActionPair(perceived, action)));
            if (cost < bestCost) {
                bestCost = cost;
                result = action;
            }
        }
        previousState = perceived;
        previousAction = result;
        return result;
    }

    @Override
    public Map<StateActionPair, State> explored() {
        return result;
    }

    @Override
    public boolean finished() {
        return goalTester.goalReached(previousState);
    }

    private double cost(Action a, State prev, State reached) {
        if (reached == null) {
            return goalTester.heuristic(prev);
        }
        return a.cost() + estimate.get(reached);
    }

}
