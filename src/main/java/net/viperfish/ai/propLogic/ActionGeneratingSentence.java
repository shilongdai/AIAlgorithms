package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.Action;

import java.util.Collection;

public abstract class ActionGeneratingSentence implements Sentence {

    @Override
    public Collection<? extends Action> availableActions() {
        return null;
    }
}
