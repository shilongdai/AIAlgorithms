package net.viperfish.ai.preceptSearch;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.perceptSearch.DeepeningOnlineDepthFirstSearch;
import net.viperfish.ai.perceptSearch.OnlineSearch;

public class DFSOnlineSearchTest extends OnlineSearchTest {
    @Override
    protected OnlineSearch<NQueenProblem> getSolver() {
        return new DeepeningOnlineDepthFirstSearch<>(new NQueenProblem(1));
    }
}
