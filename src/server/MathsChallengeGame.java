package server;

import java.awt.image.BufferedImage;

public class MathsChallengeGame {

    BufferedImage image = null;
    int solution = -1;

    /**
     * Image of the game and what is the solution to the game.
     * @param image
     * @param solution
     */
    public MathsChallengeGame(BufferedImage image, int solution) {
        super();
        this.image = image;
        this.solution = solution;
    }

    /**
     * The image of the game.
     * @return the location of the game.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return The solution of the game.
     */
    public int getSolution() {
        return solution;
    }





}
