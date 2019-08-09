package net.viperfish.ai.search.deterministic;

public class LocalBeamSearchTest extends LocalSearchTest {
    @Override
    protected LocalSearch getAlg() {
        return new LocalBeamSearch(10000);
    }
}
