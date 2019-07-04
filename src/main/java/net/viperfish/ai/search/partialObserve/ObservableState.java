package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.State;

import java.util.Collection;

public interface ObservableState extends State {

    Collection<Precept> possibleObservations();

}
