package net.viperfish.ai.localSearch;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.NQueenRandomizer;

public class SteepAscentTest extends LocalSearchTest {
    @Override
    protected LocalSearch<NQueenProblem> getAlg() {
        return new RandomRestartHillClimb<>(new SteepAscentHillClimbSearch<>(1000), new NQueenRandomizer(8));
    }
}
