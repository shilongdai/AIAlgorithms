package net.viperfish.ai.search;

import java.util.Objects;

public class StateActionPair {

    private State state;
    private Action action;

    public StateActionPair(State state, Action action) {
        this.state = state;
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateActionPair that = (StateActionPair) o;
        return Objects.equals(state, that.state) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, action);
    }
}
