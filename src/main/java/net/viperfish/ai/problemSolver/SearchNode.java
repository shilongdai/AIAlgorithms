package net.viperfish.ai.problemSolver;

import java.util.Objects;

class SearchNode<S extends State> implements Comparable<SearchNode<S>> {

    private SearchNode<S> parent;
    private Action<S> actionTaken;
    private S current;
    private double cost;

    SearchNode(SearchNode<S> parent, Action<S> actionTaken, S current, double cost) {
        this.parent = parent;
        this.actionTaken = actionTaken;
        this.current = current;
        this.cost = cost;
    }

    SearchNode<S> getParent() {
        return parent;
    }

    Action<S> getActionTaken() {
        return actionTaken;
    }

    S getCurrent() {
        return current;
    }

    double getCost() {
        return cost;
    }

    public void setParent(SearchNode<S> parent) {
        this.parent = parent;
    }

    public void setActionTaken(Action<S> actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setCurrent(S current) {
        this.current = current;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(SearchNode<S> sSearchNode) {
        return Double.compare(cost, sSearchNode.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode<?> that = (SearchNode<?>) o;
        return Double.compare(that.cost, cost) == 0 &&
                Objects.equals(parent, that.parent) &&
                Objects.equals(actionTaken, that.actionTaken) &&
                Objects.equals(current, that.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, actionTaken, current, cost);
    }
}
