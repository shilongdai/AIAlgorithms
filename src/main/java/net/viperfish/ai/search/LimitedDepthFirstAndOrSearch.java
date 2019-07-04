package net.viperfish.ai.search;

import net.viperfish.ai.search.deterministic.GoalTester;

import java.util.Map;
import java.util.Set;

public abstract class LimitedDepthFirstAndOrSearch<K> {
    public LimitedDepthFirstAndOrSearch() {
    }

    protected ORNode<K> searchAndNode(ANDNode<K> node, GoalTester goalTester, Set<State> onPath, int limit) {
        if (limit == 0) {
            return null;
        }
        State currentState = node.getCurrentState();
        if (goalTester.goalReached(currentState)) {
            return new ORNode<K>(null, null);
        }
        for (Action action : currentState.availableActions()) {
            ORNode<K> orNode = new ORNode<K>(action, currentState);
            if (searchOrNode(orNode, goalTester, onPath, limit - 1)) {
                return orNode;
            }
        }
        return null;
    }

    protected boolean searchOrNode(ORNode<K> node, GoalTester goalTester, Set<State> onPath, int limit) {
        if (limit == 0) {
            return false;
        }
        Map<K, ? extends State> possibleResults = getPossibility(node.getState(), node.getActionTaken());
        for (Map.Entry<K, ? extends State> entry : possibleResults.entrySet()) {
            if (onPath.contains(entry.getValue())) {
                return false;
            }
            onPath.add(entry.getValue());
            ANDNode<K> andNode = new ANDNode<K>(entry.getKey(), entry.getValue());
            ORNode<K> actionNode = searchAndNode(andNode, goalTester, onPath, limit);
            andNode.setNext(actionNode);
            if (actionNode == null) {
                return false;
            }
            node.addBranch(andNode.getOutcome(), andNode);
            onPath.remove(entry.getValue());
        }
        return true;
    }

    protected abstract Map<K, ? extends State> getPossibility(State current, Action actionTaken);

}
