package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface Randomizer {

    Iterable<? extends State> randomState(int amount);

    State randomState();

}
