package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.State;

public interface ObjectiveFunction<S extends State> {

    double evaluate(S state);

}
