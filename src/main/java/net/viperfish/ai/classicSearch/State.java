package net.viperfish.ai.classicSearch;

import java.util.Collection;

public interface State {

    Collection<? extends Action<? extends State>> availableActions();

}
