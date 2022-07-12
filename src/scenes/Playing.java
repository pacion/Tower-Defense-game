package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import handlers.EnemyHandler;
import helperMethods.LoadSave;
import main.Game;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private ActionBar bottomBar;
    private int mouseX, mouseY;
    private EnemyHandler enemyHandler;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        bottomBar = new ActionBar(0, 640, 640, 100, this);

        enemyHandler = new EnemyHandler(this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");

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
                graphics.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    public void update() {
        enemyHandler.update();
    }

    private BufferedImage getSprite(int spriteID) {
        return getGame().getTileManager().getSprite(spriteID);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            enemyHandler.addEnemy(x, y);
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

}