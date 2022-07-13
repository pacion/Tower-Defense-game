package handlers;

import enemies.*;
import helperMethods.LoadSave;
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
    private float speed = 0.5f;

    public EnemyHandler(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(0 * 32, 19 * 32, ORC);
        addEnemy(2 * 32, 10 * 32, BAT);
        addEnemy(8 * 32, 14 * 32, KNIGHT);
        addEnemy(8 * 32, 8 * 32, WOLF);

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
            updateEnemyMove(enemy);
        }
    }

    public void updateEnemyMove(Enemy enemy) {
        if(enemy.getLastDirection() == -1) {
            setNewDirectionAndMove(enemy);
        }

        int newX = (int)(enemy.getX() + getSpeedAndWidth(enemy.getLastDirection()));
        int newY = (int)(enemy.getY() + getSpeedAndHeight(enemy.getLastDirection()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            enemy.move(speed, enemy.getLastDirection());
        } else if(isAtEndOfPath(enemy)) {

        } else {
            setNewDirectionAndMove(enemy);
        }
    }

    private void setNewDirectionAndMove(Enemy enemy) {
        int direction = enemy.getLastDirection();

        int x = (int)(enemy.getX() / 32);
        int y = (int)(enemy.getY() / 32);

        fixEnemyOffsetTile(enemy, direction, x, y);

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int)(enemy.getY() + getSpeedAndHeight(UP));

            if(getTileType((int)enemy.getX(), newY) == ROAD_TILE) {
                enemy.move(speed, UP);
            } else {
                enemy.move(speed, DOWN);
            }
        } else if(direction == UP || direction == DOWN) {
            int newX = (int)(enemy.getY() + getSpeedAndHeight(RIGHT));

            if(getTileType(newX, (int)enemy.getY()) == ROAD_TILE) {
                enemy.move(speed, RIGHT);
            } else {
                enemy.move(speed, LEFT);
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
        return false;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int direction) {
        if(direction == UP) {
            return -speed;
        } else if(direction == DOWN) {
            return speed + 32;
        }

        return 0;
    }

    private float getSpeedAndWidth(int direction) {
        if(direction == LEFT) {
            return -speed;
        } else if(direction == RIGHT) {
            return speed + 32;
        }

        return 0;
    }

    public void addEnemy(int x, int y, int enemyType) {
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
            drawEnemy(enemy, graphics);
        }
    }

    private void drawEnemy (Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[enemy.getEnemyType()], (int)enemy.getX(), (int)enemy.getY(), null);

    }

}
