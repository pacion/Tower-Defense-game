package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class GameOver extends GameScene implements  SceneMethods {
    private MyButton buttonReplay, buttonMenu;

    public GameOver(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        int width = 150;
        int height = width / 4;
        int x = 640 / 2 - width / 2;
        int y = 260;
        int yOffset = 100;

        buttonMenu = new MyButton("Menu", x, y, width, height);
        buttonReplay = new MyButton("Replay", x, y + yOffset, width, height);
    }

    @Override
    public void render(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonReplay.draw(graphics);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 50));
        graphics.drawString("Game over!", 200, 120);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if(buttonReplay.getBounds().contains(x, y)) {
            replayGame();
        }
    }

    private void replayGame() {

    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        buttonReplay.setMouseOver(false);

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else if(buttonReplay.getBounds().contains(x, y)) {
            buttonReplay.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMousePressed(true);
        } else if(buttonReplay.getBounds().contains(x, y)) {
            buttonReplay.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonReplay.resetBooleans();
        buttonMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
