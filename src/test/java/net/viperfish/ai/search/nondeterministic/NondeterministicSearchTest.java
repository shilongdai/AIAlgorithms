package net.viperfish.ai.search.nondeterministic;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.Plan;
import net.viperfish.ai.vacuum.NondeterministicVacuumWorld;
import org.junit.Assert;
import org.junit.Test;

public abstract class NondeterministicSearchTest {

    @Test
    public void testSearch() {
        NondeterministicState initial = new NondeterministicVacuumWorld(0);
        NondeterministicSearch alg = getAlg();
        Plan<NondeterministicState> result = alg.search(initial, new NondeterministicVacuumWorld(0));
        System.out.println("Search Finished");
        while (true) {
            Action next = result.next(initial);
            if (next == null) {
                break;
            }
            initial = (NondeterministicState) next.execute(initial);
        }
        Assert.assertTrue(((NondeterministicVacuumWorld) initial).clean());
    }

    protected abstract NondeterministicSearch getAlg();

}
