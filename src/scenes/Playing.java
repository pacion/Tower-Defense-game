package scenes;

import handlers.TileHandler;
import helperMethods.LevelBuilder;
import main.Game;
import objects.Tile;
import ui.BottomBar;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class Playing extends GameScene implements SceneMethods{
    private int[][] lvl;
    private TileHandler tileHandler;
    private BottomBar bottomBar;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect = false;
    private int lastTileX, lastTileY, lastTileId;


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
                graphics.drawImage(tileHandler.getSprite(id), x * 32, y * 32, getGame());
            }
        }

        bottomBar.draw(graphics);
        drawSelectedTile(graphics);
    }

    private void drawSelectedTile(Graphics graphics) {
        if (selectedTile != null && drawSelect) {
            graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY,  32, 32, null);
        }
    }

    public void setSelectedTile(Tile tile) {
        drawSelect = true;
        this.selectedTile = tile;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    private void changeTile(int x, int y) {
        if(selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;

            if (lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId())
                return;

            if(x <= 0 || y <= 0 || x >= 640 || y >= 740)
                return;

            lastTileX = tileX;
            lastTileY = tileY;
            lastTileId = selectedTile.getId();

            lvl[tileY][tileX] = selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
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

    @Override
    public void mouseDragged(int x, int y) {
        if(y >= 640) {

        } else {
            changeTile(x, y);
        }
    }
}
