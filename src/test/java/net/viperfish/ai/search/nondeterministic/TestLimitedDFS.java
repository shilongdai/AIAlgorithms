package net.viperfish.ai.search.nondeterministic;

public class TestLimitedDFS extends NondeterministicSearchTest {
    @Override
    protected NondeterministicSearch getAlg() {
        return new LimitedNondeterministicDFS(10);
    }
}
