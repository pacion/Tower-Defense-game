package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class Settings extends GameScene implements SceneMethods {
    private MyButton buttonMenu;

    public Settings(Game game) {
        super(game);
        
        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 2, 100, 30);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,640,640);

        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y))
            SetGameState(MENU);
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        if (buttonMenu.getBounds().contains(x, y))
            buttonMenu.setMouseOver(true);
    }

    @Override
    public void mousePressed(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y))
            buttonMenu.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        buttonMenu.resetBooleans();

    }
}
