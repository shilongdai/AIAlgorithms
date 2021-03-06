package net.viperfish.ai.game;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.game.GameSearch;
import net.viperfish.ai.tictac.TicTacGame;
import org.junit.Assert;
import org.junit.Test;

public abstract class TestGame {

    @Test
    public void testGamePlay() {
        GameSearch player = getAlg();
        TicTacGame game = new TicTacGame();
        boolean first = true;
        while (!game.goalReached(game)) {
            Action action;
            action = player.search(game, game, first);
            game = (TicTacGame) action.execute(game);
            first = !first;
        }
        Assert.assertEquals(0, game.heuristic(game), Double.MIN_NORMAL);
    }

    protected abstract GameSearch getAlg();

}
