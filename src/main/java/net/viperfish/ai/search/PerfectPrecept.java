package net.viperfish.ai.search;

import java.util.Arrays;
import java.util.Collection;

public abstract class PerfectPrecept implements Precept {

    public abstract State getState();

    @Override
    public Collection<State> measure() {
        return Arrays.asList(getState());
    }
}
