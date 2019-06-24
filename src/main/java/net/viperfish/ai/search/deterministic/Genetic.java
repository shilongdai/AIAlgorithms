package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.State;

public interface Genetic {
    State reproduce(State first, State other);

    State mutate(State state);
}
