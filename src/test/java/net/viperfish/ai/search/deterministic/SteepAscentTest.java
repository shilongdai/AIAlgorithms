package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenRandomizer;

public class SteepAscentTest extends LocalSearchTest {
    @Override
    protected LocalSearch getAlg() {
        return new RandomRestartHillClimb(new SteepAscentHillClimbSearch(1000), new NQueenRandomizer(8), 10000);
    }
}
