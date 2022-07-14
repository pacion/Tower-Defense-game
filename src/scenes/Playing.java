package scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import handlers.EnemyHandler;
import handlers.TowerHandler;
import helperMethods.LoadSave;
import main.Game;
import objects.PathPoint;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {
    private int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyHandler enemyHandler;
    private TowerHandler towerHandler;
    private PathPoint start, end;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        actionBar = new ActionBar(0, 640, 640, 160, this);

        enemyHandler = new EnemyHandler(this, start, end);
        towerHandler = new TowerHandler(this);
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
        actionBar.draw(graphics);

        enemyHandler.draw(graphics);
        towerHandler.draw(graphics);
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
        towerHandler.update();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            enemyHandler.addEnemy(1);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640)
            actionBar.mouseMoved(x, y);
        else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if (xCord < 0 || xCord > 19)
            return 0;
        if (yCord < 0 || yCord > 19)
            return 0;

        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }
}