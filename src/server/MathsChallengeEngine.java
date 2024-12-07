package server;

import java.awt.image.BufferedImage;


public class MathsChallengeEngine {
    String thePlayer = null;

    public MathsChallengeEngine(String player) {
        thePlayer = player;
    }

    int counter = 0;
    int score = 0;
    MathsChallengeServer theGames = new MathsChallengeServer();
    MathsChallengeGame current = null;
    public BufferedImage nextGame() {
        current = theGames.getRandomGame();
        return current.getImage();

    }
    public boolean checkSolution( int i) {
        if (i == current.getSolution()) {
            score++;
            return true;
        } else {
            return false;
        }
    }
    public int getScore() {
        return score;
    }

}
