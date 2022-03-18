package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {
    private GameScreen gameScreen;
    private BufferedImage image;
    private Thread gameThread;
    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    public Game() {
        importImage();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(image);
        add(gameScreen);
        pack();

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

    private void start() {
        gameThread = new Thread(this) {};
        gameThread.start();
    }

    private void updateGame() {

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000 * 1000 * 1000.0 / FPS_SET;
        double timePerUpdate = 1000 * 1000 * 1000.0 / UPS_SET;
        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        long now;

        while (true) {
            now = System.nanoTime();

            if(now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            if(now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if(System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS " + frames + " | UPS " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
}
