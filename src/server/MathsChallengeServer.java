package server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class MathsChallengeServer {

    private static String readUrl(String urlString)  {
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (Exception e) {
            /* To do: proper exception handling when URL cannot be read. */
            System.out.println("An Error occured: " + e.toString());
            e.printStackTrace();
            return null;
        }

    }

    public MathsChallengeGame getRandomGame() {

        String bananaapi = "https://marcconrad.com/uob/banana/api.php?out=csv&base64=yes";
        String dataraw = readUrl(bananaapi);
        String[] data = dataraw.split(",");

        byte[] decodeImg = Base64.getDecoder().decode(data[0]);
        ByteArrayInputStream quest = new ByteArrayInputStream(decodeImg);

        int solution = Integer.parseInt(data[1]);

        BufferedImage img = null;
        try {
            img = ImageIO.read(quest);
            return new MathsChallengeGame(img, solution);
        } catch (IOException e1) {

            e1.printStackTrace();
            return null;
        }
    }

}
