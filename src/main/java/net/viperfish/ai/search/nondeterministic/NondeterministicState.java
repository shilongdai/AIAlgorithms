package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.State;

import java.util.Collection;

public interface NondeterministicState extends State {

    @Override
    Collection<? extends NondeterministicAction> availableActions();

}
