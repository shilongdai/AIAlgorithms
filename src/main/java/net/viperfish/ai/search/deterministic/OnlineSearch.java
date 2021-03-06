package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.search.PerfectPrecept;
import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.StateActionPair;

import java.util.Map;

public abstract class OnlineSearch implements Plan<PerfectPrecept> {

    public abstract Map<StateActionPair, State> explored();

}
