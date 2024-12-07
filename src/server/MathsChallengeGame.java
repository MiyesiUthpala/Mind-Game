package server;

import java.awt.image.BufferedImage;

public class MathsChallengeGame {

    BufferedImage image = null;
    int solution = -1;

    public MathsChallengeGame(BufferedImage image, int solution) {
        super();
        this.image = image;
        this.solution = solution;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getSolution() {
        return solution;
    }





}
