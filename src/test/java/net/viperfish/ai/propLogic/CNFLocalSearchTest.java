package net.viperfish.ai.propLogic;

import net.viperfish.ai.search.deterministic.LocalSearch;
import net.viperfish.ai.search.deterministic.RandomRestartHillClimb;
import net.viperfish.ai.search.deterministic.Randomizer;
import net.viperfish.ai.search.deterministic.SteepAscentHillClimbSearch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CNFLocalSearchTest {

    @Test
    public void testCNFConversion() {
        Sentence a = new LiteralSentence("a");
        Sentence b = new LiteralSentence("b");
        Sentence c = new LiteralSentence("c");

        Sentence bOrC = new DisjunctSentence(Arrays.asList(b, c));
        Sentence aEqualBOrC = new EquivalenceSentence(a, bOrC);
        System.out.println("Converting: " + aEqualBOrC.toString());

        Randomizer rand = new PropositionRandomizer(1, aEqualBOrC);
        CNFHeuristic tester = new CNFHeuristic();

        LocalSearch search = new RandomRestartHillClimb(new SteepAscentHillClimbSearch(10), rand, 10);
        Sentence result = (Sentence) search.solve(Arrays.asList(aEqualBOrC), tester, tester);

        Assert.assertNotEquals(null, result);
        Assert.assertTrue(tester.goalReached(result));
    }

}
