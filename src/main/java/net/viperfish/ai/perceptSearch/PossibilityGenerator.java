package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

import java.util.Map;

public interface PossibilityGenerator<S extends State> {

    Map<DeterminingFactor, S> generate(S parent, Action<S> actionTaken);

}
