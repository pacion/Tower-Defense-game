package scenes;

import handlers.TileHandler;
import helperMethods.LevelBuilder;
import main.Game;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods{
    private int[][] lvl;
    private TileHandler tileHandler;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuilder.getLevelData();
        tileHandler = new TileHandler();

    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < lvl.length; ++y) {
            for(int x = 0; x < lvl[y].length; ++x) {
                int id = lvl[y][x];
                graphics.drawImage(tileHandler.getSprite(id), x, y, getGame());
            }
        }
    }
}
