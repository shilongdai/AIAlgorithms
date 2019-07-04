package net.viperfish.ai.search;

public class ANDNode<K> {

    private K outcome;
    private State currentState;
    private ORNode<K> next;

    public ANDNode(K outcome, State currentState) {
        this.outcome = outcome;
        this.currentState = currentState;
    }

    public K getOutcome() {
        return outcome;
    }

    public State getCurrentState() {
        return currentState;
    }

    public ORNode<K> getNext() {
        return next;
    }

    public void setNext(ORNode<K> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "ANDNode{" +
                "outcome=" + outcome +
                ", currentState=" + currentState +
                '}';
    }
}
