package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.State;

public interface Randomizer<S extends State> {

    Iterable<S> randomState(int amount);

    S randomState();

}
