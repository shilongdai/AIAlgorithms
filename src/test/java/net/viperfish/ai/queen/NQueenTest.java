package net.viperfish.ai.queen;

import org.junit.Assert;
import org.junit.Test;

public class NQueenTest {

    @Test
    public void testNQueen() {
        NQueenProblem checker = new NQueenProblem(0);
        NQueenProblem np = new NQueenProblem(4);
        np.placeQueen(0, 2);
        np.placeQueen(1, 0);
        np.placeQueen(2, 3);
        np.placeQueen(3, 1);
        NQueenProblem npa = new NQueenProblem(4);
        npa.placeQueen(0, 2);
        npa.placeQueen(1, 0);
        npa.placeQueen(2, 3);
        npa.placeQueen(3, 1);
        Assert.assertEquals(0, checker.heuristic(np), Double.MIN_NORMAL);
        Assert.assertTrue(checker.goalReached(np));
        Assert.assertEquals(np.hashCode(), npa.hashCode());
        Assert.assertEquals(np, npa);

        PlaceQueen move1 = new PlaceQueen(0, 2);
        PlaceQueen move2 = new PlaceQueen(1, 0);
        PlaceQueen move3 = new PlaceQueen(2, 3);
        PlaceQueen move4 = new PlaceQueen(3, 1);
        NQueenProblem test = new NQueenProblem(4);
        Assert.assertTrue(test.availableActions().contains(move1));
        test = move1.execute(test);
        Assert.assertTrue(test.availableActions().contains(move2));
        test = move2.execute(test);
        Assert.assertTrue(test.availableActions().contains(move3));
        test = move3.execute(test);
        Assert.assertTrue(test.availableActions().contains(move4));

    }

}
