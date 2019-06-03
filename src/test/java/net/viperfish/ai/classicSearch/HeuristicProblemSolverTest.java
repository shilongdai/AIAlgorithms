package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;
import org.junit.Assert;
import org.junit.Test;

public abstract class HeuristicProblemSolverTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(5);
        HeuristicProblemSolver<NQueenProblem> solver = getSolver();
        for (Action<NQueenProblem> a : solver.solve(initial, new NQueenProblem(0))) {
            initial = a.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
    }

    protected abstract HeuristicProblemSolver<NQueenProblem> getSolver();

}
