package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {
    private MyButton buttonPlaying, buttonEdit, buttonSettings, buttonQuit;

    public Menu(Game game) {
        super(game);

        initButtons();
    }

    private void initButtons() {
        int width = 150;
        int height = width / 4;
        int x = 640 / 2 - width / 2;
        int y = 150;
        int yOffset = 100;

        buttonPlaying = new MyButton("Play", x, y, width, height);
        buttonEdit = new MyButton("Edit", x, y + yOffset, width, height);
        buttonSettings = new MyButton("Settings", x, y + yOffset * 2, width, height);
        buttonQuit = new MyButton("Quit", x, y + yOffset * 3, width, height);
    }

    @Override
    public void render(Graphics graphics) {
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonPlaying.draw(graphics);
        buttonEdit.draw(graphics);
        buttonSettings.draw(graphics);
        buttonQuit.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (buttonPlaying.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (buttonEdit.getBounds().contains(x, y)) {
            SetGameState(EDIT);
        } else if (buttonSettings.getBounds().contains(x, y)) {
            SetGameState(SETTINGS);
        } else if (buttonQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonPlaying.setMouseOver(false);
        buttonEdit.setMouseOver(false);
        buttonSettings.setMouseOver(false);
        buttonQuit.setMouseOver(false);

        if (buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setMouseOver(true);
        } else if (buttonEdit.getBounds().contains(x, y)) {
            buttonEdit.setMouseOver(true);
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
        } else if (buttonEdit.getBounds().contains(x, y)) {
            buttonEdit.setMousePressed(true);
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
        buttonEdit.resetBooleans();
        buttonSettings.resetBooleans();
        buttonQuit.resetBooleans();
    }
}