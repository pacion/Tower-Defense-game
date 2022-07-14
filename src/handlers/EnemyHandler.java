package handlers;

import enemies.*;
import helperMethods.LoadSave;
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

    public EnemyHandler(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        this.start = start;
        this.end = end;

        addEnemy(ORC);
        addEnemy(BAT);
        addEnemy(KNIGHT);
        addEnemy(WOLF);

        loadEnemyImages();
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

    public void updateEnemyMove(Enemy enemy) {
        if(enemy.getLastDirection() == -1) {
            setNewDirectionAndMove(enemy);
        }

        int newX = (int)(enemy.getX() + getSpeedAndWidth(enemy.getLastDirection(), enemy.getEnemyType()));
        int newY = (int)(enemy.getY() + getSpeedAndHeight(enemy.getLastDirection(), enemy.getEnemyType()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            enemy.move(GetSpeed(enemy.getEnemyType()), enemy.getLastDirection());
        } else if(isAtEndOfPath(enemy)) {
            System.out.println("koniec trasy");
        } else {
            setNewDirectionAndMove(enemy);
        }
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
        } else if(direction == UP || direction == DOWN) {
            int newX = (int)(enemy.getY() + getSpeedAndHeight(RIGHT, enemy.getEnemyType()));

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
            enemies.add(new Orc(x,  y, 0));
        } else if(enemyType == KNIGHT) {
            enemies.add(new Knight(x,  y, 0));
        } else if(enemyType == BAT) {
            enemies.add(new Bat(x,  y, 0));
        } else if(enemyType == WOLF) {
            enemies.add(new Wolf(x,  y, 0));
        }
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
                drawEnemy(enemy, graphics);
                drawHealthBar(enemy, graphics);
            }
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

}
