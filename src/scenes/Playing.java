package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handlers.EnemyHandler;
import helperMethods.LoadSave;
import main.Game;
import objects.PathPoint;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {
    private int[][] lvl;
    private ActionBar bottomBar;
    private int mouseX, mouseY;
    private EnemyHandler enemyHandler;
    private PathPoint start, end;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        bottomBar = new ActionBar(0, 640, 640, 100, this);

        enemyHandler = new EnemyHandler(this, start, end);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");
        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        bottomBar.draw(graphics);

        enemyHandler.draw(graphics);
    }

    private void drawLevel(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];

                if(isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    public void update() {
        updateTick();
        enemyHandler.update();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            enemyHandler.addEnemy(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640)
            bottomBar.mouseMoved(x, y);
        else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    public int getTileType(int x, int y) {
        int newX = x / 32;
        int newY = y / 32;

        if(newX < 0 || newX > 19 || newY < 0 || newY > 19)
            return 0;

        int id = lvl[newX][newY];

        return game.getTileManager().getTile(id).getTileType();
    }
}