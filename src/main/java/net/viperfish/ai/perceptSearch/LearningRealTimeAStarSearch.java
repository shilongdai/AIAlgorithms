package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.HeuristicGoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.HashMap;
import java.util.Map;

public class LearningRealTimeAStarSearch<S extends State> extends OnlineSearch<S> {

    private Map<StateActionPair<S>, S> result;
    private Map<S, Double> estimate;
    private S previousState;
    private Action<S> previousAction;
    private HeuristicGoalTester<S> goalTester;

    public LearningRealTimeAStarSearch(HeuristicGoalTester<S> goalTester) {
        this.goalTester = goalTester;
        result = new HashMap<>();
        estimate = new HashMap<>();
        previousAction = null;
        previousState = null;
    }

    @Override
    public Action<S> next(DeterministicPrecept<S> precept) {
        S perceived = precept.getState();
        if (goalTester.goalReached(perceived)) {
            previousState = perceived;
            return null;
        }
        if (!estimate.containsKey(perceived)) {
            estimate.put(perceived, goalTester.heuristic(perceived));
        }
        if (previousState != null) {
            this.result.put(new StateActionPair<>(previousState, previousAction), perceived);
            Action<?> best = null;
            double bestCost = Double.POSITIVE_INFINITY;
            for (Action<?> a : previousState.availableActions()) {
                if (a.cost() < bestCost) {
                    best = a;
                    bestCost = a.cost();
                }
            }
            estimate.put(previousState, cost(best, previousState, perceived));
        }
        Action<S> result = null;
        double bestCost = Double.POSITIVE_INFINITY;
        for (Action<?> a : perceived.availableActions()) {
            Action<S> action = (Action<S>) a;
            double cost = cost(a, perceived, this.result.get(new StateActionPair<>(perceived, action)));
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
    public Map<StateActionPair<S>, S> explored() {
        return result;
    }

    @Override
    public boolean finished() {
        return goalTester.goalReached(previousState);
    }

    private double cost(Action<?> a, S prev, S reached) {
        if (reached == null) {
            return goalTester.heuristic(prev);
        }
        return a.cost() + estimate.get(reached);
    }

}
