package scenes;

import handlers.TileHandler;
import helperMethods.LevelBuilder;
import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class Playing extends GameScene implements SceneMethods{
    private int[][] lvl;
    private TileHandler tileHandler;
    private MyButton buttonMenu;

    public Playing(Game game) {
        super(game);

        initButtons();
        lvl = LevelBuilder.getLevelData();
        tileHandler = new TileHandler();

    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 2, 100, 30);
    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < lvl.length; ++y) {
            for(int x = 0; x < lvl[y].length; ++x) {
                int id = lvl[y][x];
                graphics.drawImage(tileHandler.getSprite(id), x, y, getGame());
            }
        }
        
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
        buttonMenu.resetBooleans();
    }
}
