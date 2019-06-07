package net.viperfish.ai.preceptSearch;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.perceptSearch.LearningRealTimeAStarSearch;
import net.viperfish.ai.perceptSearch.OnlineSearch;

public class LRTAStarSearchTest extends OnlineSearchTest {
    @Override
    protected OnlineSearch<NQueenProblem> getSolver() {
        return new LearningRealTimeAStarSearch<>(new NQueenProblem(1));
    }
}
