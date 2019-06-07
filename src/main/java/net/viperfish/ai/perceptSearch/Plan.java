package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

public interface Plan<S extends State> {

    Action<S> next(Precept<S> precept);

    boolean finished();

}
