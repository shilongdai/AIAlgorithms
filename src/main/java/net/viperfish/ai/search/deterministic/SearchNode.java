package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Objects;

class SearchNode implements Comparable<SearchNode> {

    private SearchNode parent;
    private Action actionTaken;
    private State current;
    private double cost;

    SearchNode(SearchNode parent, Action actionTaken, State current, double cost) {
        this.parent = parent;
        this.actionTaken = actionTaken;
        this.current = current;
        this.cost = cost;
    }

    SearchNode getParent() {
        return parent;
    }

    public void setParent(SearchNode parent) {
        this.parent = parent;
    }

    Action getActionTaken() {
        return actionTaken;
    }

    double getCost() {
        return cost;
    }

    public void setActionTaken(Action actionTaken) {
        this.actionTaken = actionTaken;
    }

    State getCurrent() {
        return current;
    }

    public void setCurrent(State current) {
        this.current = current;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(SearchNode sSearchNode) {
        return Double.compare(cost, sSearchNode.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode that = (SearchNode) o;
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
