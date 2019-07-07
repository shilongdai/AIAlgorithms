package net.viperfish.ai.search.partialObserve;

public class TestDFS extends PartialObserveSearchTest {
    @Override
    protected BeliefStateSearch getAlg() {
        return new IterativeDeepeningBeliefDFS();
    }
}
