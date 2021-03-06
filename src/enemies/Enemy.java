package enemies;

import handlers.EnemyHandler;

import java.awt.*;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.GetStartHealth;

public abstract class Enemy {
    protected float x, y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int id;
    protected int enemyType;
    protected int lastDirection;
    protected boolean alive = true;
    protected int slowTickLimit = 60 * 3;
    protected int slowTick = slowTickLimit;
    protected EnemyHandler enemyHandler;

    public Enemy(float x, float y, int id, int enemyType, EnemyHandler enemyHandler) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        this.enemyHandler = enemyHandler;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDirection = -1;
        setStartHealth();
    }

    public void hurt(int damage) {
        this.health -= damage;

        if (health <= 0) {
            alive = false;
            enemyHandler.rewardPlayer(enemyType);
        }
    }

    public void slow() {
        slowTick = 0;
    }

    public void move(float speed, float direction) {
        lastDirection = (int) direction;

        float powerOfSlow = 0.5f;
        if (slowTick < slowTickLimit) {
            slowTick++;
            speed *= powerOfSlow;
        }

        if (direction == LEFT) {
            this.x -= speed;
        } else if (direction == UP) {
            this.y -= speed;
        } else if (direction == RIGHT) {
            this.x += speed;
        } else if (direction == DOWN) {
            this.y += speed;
        }

        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void kill() {
        alive = false;
        health = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }

    public float getHealthBarFloat() {
        return health / (float) maxHealth;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void setStartHealth() {
        health = GetStartHealth(enemyType);
        maxHealth = health;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public void setPositionFix(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(int newDirection) {
        this.lastDirection = newDirection;
    }
}
