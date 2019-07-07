package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.partialObserve.ObservableState;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PartiallyObservableVacuumWorld extends VacuumWorld implements ObservableState {

    public PartiallyObservableVacuumWorld(int x) {
        super(x);
    }

    public PartiallyObservableVacuumWorld(int x, VacuumWorld other) {
        super(x, other);
    }

    public PartiallyObservableVacuumWorld(int x, boolean[] dusts) {
        super(x, dusts);
    }

    @Override
    public Collection<? extends Action> availableActions() {
        Set<Action> result = new HashSet<>();
        for (Action s : super.availableActions()) {
            result.add(new PartialVacuumAction(s));
        }
        return result;
    }

    @Override
    public Collection<Precept> possibleObservations() {
        if (grid[0] && grid[1]) {
            return Arrays.asList(new DirtyPrecept(true));
        }
        if (!grid[0] && !grid[1]) {
            return Arrays.asList(new DirtyPrecept(false));
        }
        return Arrays.asList(new DirtyPrecept(true), new DirtyPrecept(false));
    }
}
