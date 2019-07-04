package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.State;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BeliefState implements State {

    private Set<ObservableState> states;

    public BeliefState(Collection<ObservableState> states) {
        this.states = new HashSet<>(states);
    }

    public Collection<ObservableState> possibilities() {
        return new HashSet<>(states);
    }

    public Collection<Precept> possiblePrecepts() {
        Set<Precept> result = new HashSet<>();
        for (ObservableState s : states) {
            result.addAll(s.possibleObservations());
        }
        return result;
    }

    @Override
    public Collection<Action> availableActions() {
        Set<Action> result = new HashSet<>();
        for (ObservableState s : states) {
            result.addAll(s.availableActions());
        }
        return result;
    }
}
