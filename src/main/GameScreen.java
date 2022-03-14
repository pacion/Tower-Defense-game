package main;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;
    private BufferedImage image;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public GameScreen(BufferedImage image) {
        this.image = image;

        loadSprites();

        random = new Random();
    }

    private void loadSprites() {

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 10; ++x) {
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);


        for(int y = 0; y < 20; ++y) {
            for(int x = 0; x < 20; ++x) {
                graphics.drawImage(sprites.get(random.nextInt(30)), x * 32, y * 32, null);
            }
        }

    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
