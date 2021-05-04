package org.game;

import org.game.breakout.BreakoutGame;

public class Main {
    /**
     * The obligatory main method that creates
     * an instance of our class and starts it running
     *
     * @param args The list of parameters this program might use (ignored)
     */
    public static void main(String[] args) {
        BreakoutGame gct = new BreakoutGame();
        gct.start(false);
    }
}
