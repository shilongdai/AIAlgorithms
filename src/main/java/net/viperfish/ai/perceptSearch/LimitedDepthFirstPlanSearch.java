package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.GoalTester;
import net.viperfish.ai.classicSearch.State;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LimitedDepthFirstPlanSearch<S extends State> implements PlanSearch<S> {

    private int limit;

    public LimitedDepthFirstPlanSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public Plan<S> solve(S initialState, GoalTester<S> goalTester, PossibilityGenerator<S> possibilityGenerator) {
        ANDNode<S> startSearch = new ANDNode<>(null, initialState);
        ORNode<S> root = searchAndNode(startSearch, goalTester, possibilityGenerator, new HashSet<>(), limit);
        if (root == null) {
            return null;
        }
        return new AndOrPlan<>(root);
    }

    private ORNode<S> searchAndNode(ANDNode<S> node, GoalTester<S> goalTester, PossibilityGenerator<S> possibilityGenerator, Set<S> onPath, int limit) {
        S currentState = node.getCurrentState();
        if (goalTester.goalReached(currentState)) {
            return new ORNode<>(null, null);
        }
        for (Action<?> a : currentState.availableActions()) {
            @SuppressWarnings("unchecked")
            Action<S> action = (Action<S>) a;
            ORNode<S> orNode = new ORNode<>(action, currentState);
            if (searchOrNode(orNode, goalTester, possibilityGenerator, onPath, limit--)) {
                return orNode;
            }
        }
        return null;
    }

    private boolean searchOrNode(ORNode<S> node, GoalTester<S> goalTester, PossibilityGenerator<S> possibilityGenerator, Set<S> onPath, int limit) {
        if (limit == 0) {
            return false;
        }
        Map<DeterminingFactor, S> possibleResults = possibilityGenerator.generate(node.getState(), node.getActionTaken());
        for (Map.Entry<DeterminingFactor, S> entry : possibleResults.entrySet()) {
            if (onPath.contains(entry.getValue())) {
                return false;
            }
            onPath.add(entry.getValue());
            ANDNode<S> andNode = new ANDNode<>(entry.getKey(), entry.getValue());
            ORNode<S> actionNode = searchAndNode(andNode, goalTester, possibilityGenerator, onPath, limit);
            if (actionNode == null) {
                return false;
            }
            node.addBranch(andNode.getOutcome(), andNode);
            onPath.remove(entry.getValue());
        }
        return true;
    }

}
