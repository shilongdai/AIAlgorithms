package net.viperfish.ai.search.partialObserve;

public class TestLimitedDFS extends PartialObserveSearchTest {
    @Override
    protected BeliefStateSearch getAlg() {
        return new LimitedBeliefStateDFS(6);
    }
}
