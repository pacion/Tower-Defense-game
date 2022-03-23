package scenes;

import handlers.TileHandler;
import helperMethods.LevelBuilder;
import main.Game;
import ui.BottomBar;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class Playing extends GameScene implements SceneMethods{
    private int[][] lvl;
    private TileHandler tileHandler;
    private BottomBar bottomBar;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuilder.getLevelData();
        tileHandler = new TileHandler();
        bottomBar = new BottomBar(0, 640, 640, 100, this);
    }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < lvl.length; ++y) {
            for(int x = 0; x < lvl[y].length; ++x) {
                int id = lvl[y][x];
                graphics.drawImage(tileHandler.getSprite(id), x, y, getGame());
            }
        }

        bottomBar.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseClicked(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseMoved(x, y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }
}
