package handlers;

import enemies.*;
import helperMethods.LoadSave;
import helperMethods.Utils;
import objects.PathPoint;
import scenes.Playing;
import static helperMethods.Constants.Enemies.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Tiles.*;

public class EnemyHandler {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;
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
        System.out.println("elo");
        tempMethod();
    }

    private void loadRoadDirectionArray() {
        roadDirectionArray = Utils.GetRoadDirectionArray(playing.getGame().getTileHandler().getTypeArray(), start, end);

    }

    private void tempMethod() {
		int[][] arr = Utils.GetRoadDirectionArray(playing.getGame().getTileHandler().getTypeArray(), start, end);

		for (int j = 0; j < arr.length; j++) {
			for (int i = 0; i < arr[j].length; i++) {
				System.out.print(arr[j][i] + "|");
			}
			System.out.println();
		}

	}


    private void loadEffectImage() {
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }
    }

    public void update() {
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
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

    private boolean isTilesTheSame(PathPoint currentTile, PathPoint newTile) {
        if (currentTile.getX() == newTile.getX())
            if (currentTile.getY() == newTile.getY())
                return true;

        return false;
    }

    private void setNewDirectionAndMove(Enemy enemy) {
        int direction = enemy.getLastDirection();

        int x = (int)(enemy.getX() / 32);
        int y = (int)(enemy.getY() / 32);

        fixEnemyOffsetTile(enemy, direction, x, y);

        if(isAtEndOfPath(enemy)) {
            return;
        }

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int)(enemy.getY() + getSpeedAndHeight(UP, enemy.getEnemyType()));

            if(getTileType((int)enemy.getX(), newY) == ROAD_TILE) {
                enemy.move(GetSpeed(enemy.getEnemyType()), UP);
            } else {
                enemy.move(GetSpeed(enemy.getEnemyType()), DOWN);
            }
        } else if(direction == UP || direction == DOWN || direction == -1) {
            int newX = (int)(enemy.getX() + getSpeedAndWidth(RIGHT, enemy.getEnemyType()));

            if(getTileType(newX, (int)enemy.getY()) == ROAD_TILE) {
                enemy.move(GetSpeed(enemy.getEnemyType()), RIGHT);
            } else {
                enemy.move(GetSpeed(enemy.getEnemyType()), LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy enemy, int direction, int x, int y) {
        if(direction == LEFT) {
            if(x > 0) {
                x--;
            }
        } else if(direction == UP) {
            if(y > 0) {
                y--;
            }
        } else if(direction == RIGHT) {
            if(x < 19) {
                x++;
            }
        } else if(direction == DOWN) {
            if(y < 19) {
                y++;
            }
        }

        enemy.setPositionFix(x * 32, y * 32);
    }

    private boolean isAtEndOfPath(Enemy enemy) {
        return enemy.getX() == end.getX() * 32 && enemy.getY() == end.getY() * 32;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int direction, int enemyType) {
        if(direction == UP) {
            return -GetSpeed(enemyType);
        } else if(direction == DOWN) {
            return GetSpeed(enemyType) + 32;
        }

        return 0;
    }

    private float getSpeedAndWidth(int direction, int enemyType) {
        if(direction == LEFT) {
            return -GetSpeed(enemyType);
        } else if(direction == RIGHT) {
            return GetSpeed(enemyType) + 32;
        }

        return 0;
    }

    public void addEnemy(int enemyType) {
        int x = start.getX() * 32;
        int y = start.getY() * 32;

        if(enemyType == ORC) {
            enemies.add(new Orc(x,  y, 0, this));
        } else if(enemyType == KNIGHT) {
            enemies.add(new Knight(x,  y, 0, this));
        } else if(enemyType == BAT) {
            enemies.add(new Bat(x,  y, 0, this));
        } else if(enemyType == WOLF) {
            enemies.add(new Wolf(x,  y, 0, this));
        }
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
                drawEnemy(enemy, graphics);
                drawHealthBar(enemy, graphics);
                drawEffects(enemy, graphics);
            }
        }
    }

    private void drawEffects(Enemy enemy, Graphics graphics) {
        if(enemy.isSlowed()) {
            graphics.drawImage(slowEffect, (int)enemy.getX(),  (int)enemy.getY(), null);
        }
    }

    private void drawHealthBar(Enemy enemy, Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect((int)enemy.getX() + 16 - (getNewBarWidth(enemy) / 2), (int)enemy.getY() - 10, getNewBarWidth(enemy), 3);
    }

    private int getNewBarWidth(Enemy enemy) {
        return (int)(HPBarWidth * enemy.getHealthBarFloat());
    }

    private void drawEnemy (Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[enemy.getEnemyType()], (int)enemy.getX(), (int)enemy.getY(), null);

    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public int getAmountOfAliveEnemies() {
        return (int) enemies.stream()
                .filter(Enemy::isAlive)
                .count();
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPLayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }
}
