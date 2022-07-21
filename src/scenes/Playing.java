package scenes;

import enemies.Enemy;
import handlers.EnemyHandler;
import handlers.ProjectileHandler;
import handlers.TowerHandler;
import handlers.WaveHandler;
import helperMethods.Constants;
import helperMethods.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static helperMethods.Constants.Tiles.GRASS_TILE;
import static main.GameStates.GAME_OVER;
import static main.GameStates.SetGameState;

public class Playing extends GameScene implements SceneMethods {
    private int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyHandler enemyHandler;
    private TowerHandler towerHandler;
    private WaveHandler waveHandler;
    private Tower selectedTower;
    private PathPoint start, end;
    private ProjectileHandler projectileHandler;
    private int goldTick = 0;
    private boolean gamePaused;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();

        actionBar = new ActionBar(0, 640, 640, 160, this);

        enemyHandler = new EnemyHandler(this, start, end);
        towerHandler = new TowerHandler(this);
        projectileHandler = new ProjectileHandler(this);
        waveHandler = new WaveHandler(this);
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints();
        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update() {
        if (!gamePaused) {
            updateTick();

            goldTick++;

            if (goldTick % (60 * 3) == 0) {
                actionBar.addGold(3);
            }

            if (isAllEnemiesDead()) {
                if (isThereMoreWaves()) {
                    waveHandler.startWaveTimer();
                    if (isWaveTimerOver()) {
                        waveHandler.increaseWaveIndex();
                        enemyHandler.getEnemies().clear();
                        waveHandler.resetEnemyIndex();
                    }
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        SetGameState(GAME_OVER);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (isTimeForNewEnemy()) {
                spawnEnemy();
            }

            enemyHandler.update();
            towerHandler.update();
            projectileHandler.update();
            waveHandler.update();
        }
    }

    private boolean isWaveTimerOver() {
        return waveHandler.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return waveHandler.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if (waveHandler.isThereMoreEnemiesInWave()) {
            return false;
        }

        for (Enemy enemy : enemyHandler.getEnemies()) {
            if (enemy.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private boolean isTimeForNewEnemy() {
        if (waveHandler.isTimeForNewEnemy()) {
            return waveHandler.isThereMoreEnemiesInWave();
        }

        return false;
    }

    private void spawnEnemy() {
        enemyHandler.spawnEnemy(waveHandler.getNextEnemy());
    }


    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);

        actionBar.draw(graphics);
        enemyHandler.draw(graphics);
        towerHandler.draw(graphics);
        projectileHandler.draw(graphics);

        drawSelectedTower(graphics);
        drawHighLight(graphics);
    }

    private void drawHighLight(Graphics graphics) {
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawSelectedTower(Graphics graphics) {
        if (selectedTower != null) {
            graphics.drawImage(towerHandler.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
        }
    }

    private void drawLevel(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];

                if (isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerHandler.addTower(selectedTower, mouseX, mouseY);
                        removeGold(selectedTower.getTowerType());

                        selectedTower = null;
                    }
                }
            } else {
                Tower tower = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(tower);
            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);
    }

    private Tower getTowerAt(int x, int y) {
        return towerHandler.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileHandler().getTile(id).getTileType();

        return tileType == GRASS_TILE;
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
        return game.getTileHandler().getTile(id).getTileType();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    public TowerHandler getTowerHandler() {
        return towerHandler;
    }

    public EnemyHandler getEnemyHandler() {
        return enemyHandler;
    }

    public WaveHandler getWaveHandler() {
        return waveHandler;
    }

    public void rewardPLayer(int enemyType) {
        actionBar.addGold(Constants.Enemies.GetReward(enemyType));
    }

    public void shootEnemy(Tower tower, Enemy enemy) {
        projectileHandler.newProjectile(tower, enemy);
    }

    public void removeTower(Tower displayedTower) {
        towerHandler.removeTower(displayedTower);
    }

    public void upgradeTower(Tower displayedTower) {
        towerHandler.upgradeTower(displayedTower);
    }

    public void removeOneHeart() {
        actionBar.removeOneHeart();
    }

    public void resetEverything() {
        actionBar.resetEverything();
        enemyHandler.reset();
        towerHandler.reset();
        projectileHandler.reset();
        waveHandler.reset();

        mouseX = 0;
        mouseY = 0;
        selectedTower = null;
        goldTick = 0;
        gamePaused = false;
    }
}