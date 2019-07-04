package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

public class PartialVacuumAction implements Action {

    private Action action;

    public PartialVacuumAction(Action action) {
        this.action = action;
    }

    @Override
    public State execute(State current) {
        VacuumWorld result = (VacuumWorld) action.execute(current);
        return new PartiallyObservableVacuumWorld(result.x, result);
    }

    @Override
    public double cost() {
        return action.cost();
    }

    @Override
    public Action reverse() {
        return action.reverse();
    }
}
