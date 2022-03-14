package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {

    private static int width = 656;
    private static int height = 646;
    private GameScreen gameScreen;
    private BufferedImage image;

    public Game() {

        importImage();

        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(image);
        add(gameScreen);
        setVisible(true);
    }

    private void importImage() {

        InputStream is = getClass().getResourceAsStream("/res/spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException event) {
            event.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
