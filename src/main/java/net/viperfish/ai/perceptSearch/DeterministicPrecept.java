package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.State;

import java.util.Arrays;
import java.util.Collection;

public abstract class DeterministicPrecept<S extends State> implements Precept<S> {

    public abstract S getState();

    @Override
    public Collection<S> possibleStates() {
        return Arrays.asList(getState());
    }
}
