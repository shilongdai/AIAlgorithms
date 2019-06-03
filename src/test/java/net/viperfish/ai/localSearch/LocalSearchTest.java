package net.viperfish.ai.localSearch;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.NQueenRandomizer;
import org.junit.Assert;
import org.junit.Test;

public abstract class LocalSearchTest {

    @Test
    public void testLocalSearch() {
        int k = 50;
        NQueenRandomizer rand = new NQueenRandomizer(8);
        Iterable<NQueenProblem> initial = rand.randomState(k);
        LocalSearch<NQueenProblem> solver = getAlg();
        NQueenProblem solution = solver.solve(initial, new NQueenProblem(0), new NQueenProblem(0));
        Assert.assertTrue(solution.goalReached(solution));
        System.out.println(solution);
    }

    protected abstract LocalSearch<NQueenProblem> getAlg();

}
