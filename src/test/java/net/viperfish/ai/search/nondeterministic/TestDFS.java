package net.viperfish.ai.search.nondeterministic;

public class TestDFS extends NondeterministicSearchTest {
    @Override
    protected NondeterministicSearch getAlg() {
        return new IterativeDeepeningNondeterministicDFS();
    }
}
