package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.State;

public interface GeneticState extends State {
    GeneticState reproduce(GeneticState other);

    void mutate();
}
