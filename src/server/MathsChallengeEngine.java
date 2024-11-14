package server;

import java.awt.image.BufferedImage;

/**
 * Main class where the games are coming from.
 *
 */
public class MathsChallengeEngine {
    String thePlayer = null;

    /**
     * Each player has their own game engine.
     *
     * @param player
     */
    public MathsChallengeEngine(String player) {
        thePlayer = player;
    }

    int counter = 0;
    int score = 0;
    MathsChallengeServer theGames = new MathsChallengeServer();
    MathsChallengeGame current = null;

    /**
     * Retrieves a game. This basic version only has two games that alternate.
     */
    public BufferedImage nextGame() {
        current = theGames.getRandomGame();
        return current.getImage();

    }

    /**
     * Checks if the parameter i is a solution to the game URL. If so, score is
     * increased by one.
     *
     * @param game
     * @param i
     * @return
     */
    public boolean checkSolution( int i) {
        if (i == current.getSolution()) {
            score++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the score.
     *
     * @param player
     * @return
     */
    public int getScore() {
        return score;
    }

}
