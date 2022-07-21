package handlers;

import enemies.*;
import helperMethods.LoadSave;
import helperMethods.Utils;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.*;
import static helperMethods.Constants.Tiles.ROAD_TILE;

public class EnemyHandler {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start;
    private PathPoint end;
    private int HPBarWidth = 20;
    private BufferedImage slowEffect;
    private int[][] roadDirectionArray;

    public EnemyHandler(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        this.start = start;
        this.end = end;

        loadEffectImage();
        loadEnemyImages();
        loadRoadDirectionArray();
    }

    private void loadEffectImage() {
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for (int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }
    }

    private void loadRoadDirectionArray() {
        roadDirectionArray = Utils.GetRoadDirectionArray(playing.getGame().getTileHandler().getTypeArray(), start, end);

    }

    public void update() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                updateEnemyMove(enemy);
            }
        }
    }

    private void updateEnemyMove(Enemy enemy) {
        PathPoint currentTile = getEnemyTile(enemy);
        int dir = roadDirectionArray[currentTile.getY()][currentTile.getX()];

        enemy.move(GetSpeed(enemy.getEnemyType()), dir);

        PathPoint newTile = getEnemyTile(enemy);

        if (!isTilesTheSame(currentTile, newTile)) {
            if (isTilesTheSame(newTile, end)) {
                enemy.kill();
                playing.removeOneHeart();
                return;
            }

            int newDirection = roadDirectionArray[newTile.getY()][newTile.getX()];
            if (newDirection != dir) {
                enemy.setPositionFix(newTile.getX() * 32, newTile.getY() * 32);
                enemy.setLastDirection(newDirection);
            }
        }

    }

    private boolean isTilesTheSame(PathPoint currentTile, PathPoint newTile) {
        if (currentTile.getX() == newTile.getX())
            return currentTile.getY() == newTile.getY();

        return false;
    }

    private PathPoint getEnemyTile(Enemy enemy) {
        switch (enemy.getLastDirection()) {
            case LEFT:
                return new PathPoint((int) ((enemy.getX() + 31) / 32), (int) (enemy.getY() / 32));
            case UP:
                return new PathPoint((int) (enemy.getX() / 32), (int) ((enemy.getY() + 31) / 32));
            case RIGHT:
            case DOWN:
                return new PathPoint((int) (enemy.getX() / 32), (int) (enemy.getY() / 32));
        }

        return new PathPoint((int) (enemy.getX() / 32), (int) (enemy.getY() / 32));
    }

    public void addEnemy(int enemyType) {
        int x = start.getX() * 32;
        int y = start.getY() * 32;

        if (enemyType == ORC) {
            enemies.add(new Orc(x, y, 0, this));
        } else if (enemyType == KNIGHT) {
            enemies.add(new Knight(x, y, 0, this));
        } else if (enemyType == BAT) {
            enemies.add(new Bat(x, y, 0, this));
        } else if (enemyType == WOLF) {
            enemies.add(new Wolf(x, y, 0, this));
        }
    }

    public void draw(Graphics graphics) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                drawEnemy(enemy, graphics);
                drawHealthBar(enemy, graphics);
                drawEffects(enemy, graphics);
            }
        }
    }

    private void drawEnemy(Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(), null);

    }

    private void drawHealthBar(Enemy enemy, Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect((int) enemy.getX() + 16 - (getNewBarWidth(enemy) / 2), (int) enemy.getY() - 10, getNewBarWidth(enemy), 3);
    }

    private void drawEffects(Enemy enemy, Graphics graphics) {
        if (enemy.isSlowed()) {
            graphics.drawImage(slowEffect, (int) enemy.getX(), (int) enemy.getY(), null);
        }
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPLayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }

    private int getNewBarWidth(Enemy enemy) {
        return (int) (HPBarWidth * enemy.getHealthBarFloat());
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies() {
        return (int) enemies.stream()
                .filter(Enemy::isAlive)
                .count();
    }
}
