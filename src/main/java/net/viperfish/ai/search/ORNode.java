package net.viperfish.ai.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ORNode<K> {

    private Map<K, ANDNode<K>> branches;
    private State state;
    private Action actionTaken;

    public ORNode(Action actionTaken, State state) {
        this.actionTaken = actionTaken;
        this.state = state;
        branches = new HashMap<>();
    }

    public Action getActionTaken() {
        return actionTaken;
    }

    public ANDNode<K> branch(K outcome) {
        return branches.get(outcome);
    }

    public void addBranch(K outcome, ANDNode<K> branch) {
        branches.put(outcome, branch);
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ORNode<?> orNode = (ORNode<?>) o;
        return Objects.equals(branches, orNode.branches) &&
                Objects.equals(state, orNode.state) &&
                Objects.equals(actionTaken, orNode.actionTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branches, state, actionTaken);
    }
}
