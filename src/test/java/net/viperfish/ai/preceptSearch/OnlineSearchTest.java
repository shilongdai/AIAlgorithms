package net.viperfish.ai.preceptSearch;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.PerfectNQueenPrecept;
import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.perceptSearch.DeterministicPrecept;
import net.viperfish.ai.perceptSearch.OnlineSearch;
import org.junit.Assert;
import org.junit.Test;

public abstract class OnlineSearchTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(8);
        OnlineSearch<NQueenProblem> solver = getSolver();
        while (true) {
            DeterministicPrecept<NQueenProblem> precept = new PerfectNQueenPrecept(initial);
            Action<NQueenProblem> next = solver.next(precept);
            if (next == null) {
                break;
            }
            initial = next.predict(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
        Assert.assertTrue(solver.finished());
    }

    protected abstract OnlineSearch<NQueenProblem> getSolver();

}
