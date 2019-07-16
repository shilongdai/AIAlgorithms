package net.viperfish.ai.game;

import net.viperfish.ai.search.game.GameSearch;
import net.viperfish.ai.search.game.LimitedDepthMinimaxSearch;

public class StandardMinimaxTest extends TestGame {
    @Override
    protected GameSearch getAlg() {
        return new LimitedDepthMinimaxSearch(10);
    }
}
