package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Collection;

public interface NondeterministicAction extends Action {

    @Override
    NondeterministicState execute(State current);

    Collection<NondeterministicState> possibilities(NondeterministicState current);

}
