package net.viperfish.ai.search.partialObserve;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Plan;
import net.viperfish.ai.search.Precept;
import net.viperfish.ai.vacuum.DirtyPrecept;
import net.viperfish.ai.vacuum.PartiallyObservableVacuumWorld;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public abstract class PartialObserveSearchTest {

    @Test
    public void testSearch() {
        PartiallyObservableVacuumWorld poss1 = new PartiallyObservableVacuumWorld(0, new boolean[]{true, true});
        PartiallyObservableVacuumWorld poss2 = new PartiallyObservableVacuumWorld(0, new boolean[]{true, false});
        PartiallyObservableVacuumWorld poss3 = new PartiallyObservableVacuumWorld(0, new boolean[]{false, true});
        PartiallyObservableVacuumWorld poss4 = new PartiallyObservableVacuumWorld(0, new boolean[]{false, false});

        PartiallyObservableVacuumWorld poss5 = new PartiallyObservableVacuumWorld(1, new boolean[]{true, true});
        PartiallyObservableVacuumWorld poss6 = new PartiallyObservableVacuumWorld(1, new boolean[]{true, false});
        PartiallyObservableVacuumWorld poss7 = new PartiallyObservableVacuumWorld(1, new boolean[]{false, true});
        PartiallyObservableVacuumWorld poss8 = new PartiallyObservableVacuumWorld(1, new boolean[]{false, false});

        BeliefState initial = new BeliefState(Arrays.asList(poss1, poss2, poss3, poss4, poss5, poss6, poss7, poss8));
        BeliefStateSearch alg = getAlg();
        Plan<Precept> result = alg.solve(initial, new BeliefStateGoalTester(new PartiallyObservableVacuumWorld(0)));
        System.out.println("Search Finished");
        while (true) {
            Action next = result.next(new DirtyPrecept(poss1.currentClean()));
            if (next == null) {
                break;
            }
            poss1 = (PartiallyObservableVacuumWorld) next.execute(poss1);
        }
        Assert.assertTrue(poss1.clean());
    }

    protected abstract BeliefStateSearch getAlg();

}
