package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

import java.util.Objects;

public class StateActionPair<S extends State> {

    private S state;
    private Action<S> action;

    public StateActionPair(S state, Action<S> action) {
        this.state = state;
        this.action = action;
    }

    public S getState() {
        return state;
    }

    public Action<S> getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateActionPair<?> that = (StateActionPair<?>) o;
        return Objects.equals(state, that.state) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, action);
    }
}
