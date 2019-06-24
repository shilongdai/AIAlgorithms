package net.viperfish.ai.search;

import java.util.Collection;

public interface State {

    Collection<? extends Action> availableActions();

}
