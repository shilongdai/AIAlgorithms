package net.viperfish.ai.search;

import net.viperfish.ai.search.deterministic.GoalTester;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class LimitedDepthFirstPlanSearch implements PlanSearch {

    private int limit;

    public LimitedDepthFirstPlanSearch(int limit) {
        this.limit = limit;
    }

    @Override
    public Plan solve(State initialState, GoalTester goalTester) {
        ANDNode startSearch = new ANDNode(null, initialState);
        ORNode root = searchAndNode(startSearch, goalTester, new HashSet<>(), limit);
        if (root == null) {
            return null;
        }
        return new AndOrPlan(root);
    }

    private ORNode searchAndNode(ANDNode node, GoalTester goalTester, Set<State> onPath, int limit) {
        State currentState = node.getCurrentState();
        if (goalTester.goalReached(currentState)) {
            return new ORNode(null, null);
        }
        for (Action action : currentState.availableActions()) {
            ORNode orNode = new ORNode(action, currentState);
            if (searchOrNode(orNode, goalTester, onPath, limit--)) {
                return orNode;
            }
        }
        return null;
    }

    private boolean searchOrNode(ORNode node, GoalTester goalTester, Set<State> onPath, int limit) {
        if (limit == 0) {
            return false;
        }
        Map<Precept, State> possibleResults = getPossibility(node.getState(), node.getActionTaken());
        for (Map.Entry<Precept, State> entry : possibleResults.entrySet()) {
            if (onPath.contains(entry.getValue())) {
                return false;
            }
            onPath.add(entry.getValue());
            ANDNode andNode = new ANDNode(entry.getKey(), entry.getValue());
            ORNode actionNode = searchAndNode(andNode, goalTester, onPath, limit);
            if (actionNode == null) {
                return false;
            }
            node.addBranch(andNode.getOutcome(), andNode);
            onPath.remove(entry.getValue());
        }
        return true;
    }

    protected abstract Map<Precept, State> getPossibility(State current, Action actionTaken);

}
