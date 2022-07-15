package enemies;

import java.awt.*;
import java.awt.image.renderable.RenderableImage;
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

    public Enemy(float x, float y, int id, int enemyType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int)x, (int)y, 32, 32);
        lastDirection = -1;
        setStartHealth();
    }

    private void setStartHealth() {
        health = GetStartHealth(enemyType);
        maxHealth = health;
    }

    public void hurt(int damage) {
        this.health -= damage;

        if(health <= 0) {
            alive = false;
        }
    }

    public void move(float speed, float direction) {
        lastDirection = (int)direction;

        if(direction == LEFT) {
            this.x -= speed;
        } else if(direction == UP) {
            this.y -= speed;
        } else if(direction == RIGHT) {
            this.x += speed;
        } else if(direction == DOWN) {
            this.y += speed;
        }

        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int)x;
        bounds.y = (int)y;
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

    public boolean isAlive() {
        return alive;
    }
}
