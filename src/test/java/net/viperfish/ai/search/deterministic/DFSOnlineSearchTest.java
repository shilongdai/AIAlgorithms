package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenProblem;

public class DFSOnlineSearchTest extends OnlineSearchTest {
    @Override
    protected OnlineSearch getSolver() {
        return new DeepeningOnlineDepthFirstSearch(new NQueenProblem(1));
    }
}
