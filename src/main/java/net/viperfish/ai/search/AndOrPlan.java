package net.viperfish.ai.search;

public class AndOrPlan<K> implements Plan<K> {

    private ANDNode<K> and;
    private ORNode<K> current;
    private boolean endReached;

    public AndOrPlan(ANDNode<K> root) {
        this.and = root;
        endReached = false;
    }

    @Override
    public Action next(K precept) {
        if (current == null) {
            current = and.getNext();
            return current.getActionTaken();
        }
        and = current.branch(precept);
        if (and == null) {
            endReached = true;
            return null;
        }
        current = and.getNext();
        return current.getActionTaken();
    }

    @Override
    public boolean finished() {
        return endReached;
    }
}
