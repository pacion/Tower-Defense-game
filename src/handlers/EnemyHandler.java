package handlers;

import enemies.Enemy;
import helperMethods.LoadSave;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyHandler {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyHandler(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(3 * 32, 9 * 32);

        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = atlas.getSubimage(0 * 32, 32, 32, 32);
        enemyImages[1] = atlas.getSubimage(1 * 32, 32, 32, 32);
        enemyImages[2] = atlas.getSubimage(2 * 32, 32, 32, 32);
        enemyImages[3] = atlas.getSubimage(3 * 32, 32, 32, 32);
    }

    public void update() {
        for(Enemy enemy : enemies) {
            enemy.move(1f, 0.4f);
        }
    }

    public void addEnemy(int x, int y) {
        enemies.add(new Enemy(x,  y, 0, 0));
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy : enemies) {
            drawEnemy(enemy, graphics);
        }
    }

    private void drawEnemy (Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[0], (int)enemy.getX(), (int)enemy.getY(), null);

    }

}
