package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenProblem;
import net.viperfish.ai.queen.PerfectNQueenPrecept;
import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.PerfectPrecept;
import org.junit.Assert;
import org.junit.Test;

public abstract class OnlineSearchTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(8);
        OnlineSearch solver = getSolver();
        while (true) {
            PerfectPrecept precept = new PerfectNQueenPrecept(initial);
            Action next = solver.next(precept);
            if (next == null) {
                break;
            }
            initial = (NQueenProblem) next.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
        Assert.assertTrue(solver.finished());
    }

    protected abstract OnlineSearch getSolver();

}
