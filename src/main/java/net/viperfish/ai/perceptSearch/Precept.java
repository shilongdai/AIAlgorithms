package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.State;

import java.util.Collection;

public interface Precept<S extends State> extends DeterminingFactor {

    Collection<S> possibleStates();

}
