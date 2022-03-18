package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {
    private Random random;
    private BufferedImage image;
    private Dimension size;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public GameScreen(BufferedImage image) {
        this.image = image;

        setPanelSize();
        loadSprites();

        random = new Random();
    }

    private void setPanelSize() {
        size = new Dimension(640, 640);

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void loadSprites() {
        int rowsOfSpriteatlas = 3;
        int colsOfSpriteatlas = 10;

        for(int y = 0; y < rowsOfSpriteatlas; ++y)
            for(int x = 0; x < colsOfSpriteatlas; ++x)
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for(int y = 0; y < 20; ++y)
            for(int x = 0; x < 20; ++x)
                graphics.drawImage(sprites.get(random.nextInt(30)), x * 32, y * 32, null);

        repaint();
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
