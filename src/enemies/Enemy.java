package enemies;

import java.awt.*;
import java.awt.image.renderable.RenderableImage;
import static helperMethods.Constants.Direction.*;

public class Enemy {
    private float x, y;
    private Rectangle bounds;
    private int health;
    private int id;
    private int enemyType;
    private int lastDirection;

    public Enemy(float x, float y, int id, int enemyType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int)x, (int)y, 32, 32);
        lastDirection = RIGHT;
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

}
