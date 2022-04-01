package scenes;

import main.Game;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods{
    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private MyButton buttonPlaying, buttonSettings, buttonQuit;

    public Menu(Game game) {
        super(game);

        importImage();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 4;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        buttonPlaying = new MyButton("Play", x, y, w, h);
        buttonSettings = new MyButton("Settings", x, y + yOffset, w, h);
        buttonQuit = new MyButton("Quit", x, y + yOffset * 2, w, h);
    }

    @Override
    public void render(Graphics graphics) {
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonPlaying.draw(graphics);
        buttonSettings.draw(graphics);
        buttonQuit.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (buttonPlaying.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (buttonSettings.getBounds().contains(x, y)) {
            SetGameState(SETTINGS);
        } else if (buttonQuit.getBounds().contains(x, y))
            System.exit(0);
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonPlaying.setMouseOver(false);
        buttonSettings.setMouseOver(false);
        buttonQuit.setMouseOver(false);

        if (buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setMouseOver(true);
        } else if (buttonSettings.getBounds().contains(x, y)) {
            buttonSettings.setMouseOver(true);
        } else if (buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setMousePressed(true);
        } else if (buttonSettings.getBounds().contains(x, y)) {
            buttonSettings.setMousePressed(true);
        } else if (buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setMousePressed(true);
        }

    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        buttonPlaying.resetBooleans();
        buttonSettings.resetBooleans();
        buttonQuit.resetBooleans();
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/sprites/spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException event) {
            event.printStackTrace();
        }
    }

    private void loadSprites() {
        int rowsOfSpriteatlas = 3;
        int colsOfSpriteatlas = 10;

        for(int y = 0; y < rowsOfSpriteatlas; ++y)
            for(int x = 0; x < colsOfSpriteatlas; ++x)
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
    }
}