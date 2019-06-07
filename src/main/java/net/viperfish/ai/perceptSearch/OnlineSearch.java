package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

import java.util.Map;

public abstract class OnlineSearch<S extends State> implements Plan<S> {

    @Override
    public Action<S> next(Precept<S> precept) {
        if (precept instanceof DeterministicPrecept) {
            return next((DeterministicPrecept<S>) precept);
        }
        throw new IllegalArgumentException(precept.getClass().toGenericString());
    }

    public abstract Action<S> next(DeterministicPrecept<S> precept);

    public abstract Map<StateActionPair<S>, S> explored();


}
