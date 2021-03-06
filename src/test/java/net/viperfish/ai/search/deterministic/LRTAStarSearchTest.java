package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenProblem;

public class LRTAStarSearchTest extends OnlineSearchTest {
    @Override
    protected OnlineSearch getSolver() {
        return new LearningRealTimeAStarSearch(new NQueenProblem(1));
    }
}
