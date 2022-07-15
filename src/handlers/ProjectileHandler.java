package handlers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Projectiles.*;
import static helperMethods.Constants.Towers.*;

public class ProjectileHandler {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projectileImages;
    private int projectileId = 0;

    public ProjectileHandler(Playing playing) {
        this.playing = playing;

        importImages();
    }

    private void importImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        projectileImages = new BufferedImage[3];

        for(int i = 0; i < 3; i++) {
            projectileImages[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        }
    }

    public void newProjectile(Tower tower, Enemy enemy) {
        int type = getProjectileType(tower);

        int xDistance = (int)Math.abs(tower.getX() - enemy.getX());
        int yDistance = (int)Math.abs(tower.getY() - enemy.getY());
        int sumOfDistances = xDistance + yDistance;

        float xPercentage = (float)xDistance / sumOfDistances;

        float xSpeed = xPercentage * GetSpeed(type);
        float ySpeed = GetSpeed(type) - xSpeed;

        if(tower.getX() > enemy.getX())
            xSpeed *= -1;

        if(tower.getY() > enemy.getY())
            ySpeed *= -1;

        projectiles.add(new Projectile(
                tower.getX() + 16,
                tower.getY() + 16, xSpeed, ySpeed, tower.getDamage(), projectileId++, type));
    }

    public void update() {
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                projectile.move();

                if(isProjectileHittingEnemy(projectile)) {
                    projectile.setActive(false);
                }
            }
        }
    }

    private boolean isProjectileHittingEnemy(Projectile projectile) {
        for(Enemy enemy : playing.getEnemyHandler().getEnemies()) {
            if(enemy.getBounds().contains(projectile.getPosition())) {
                enemy.hurt(projectile.getDamage());
                return true;
            }
        }

        return false;
    }

    public void draw(Graphics graphics) {
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                graphics.drawImage(projectileImages[projectile.getProjectileType()], (int) projectile.getPosition().x, (int) projectile.getPosition().y, null);
            }
        }
    }

    private int getProjectileType(Tower tower) {
        if(tower.getTowerType() == ARCHER) {
            return ARROW;
        } else if(tower.getTowerType() == CANNON) {
            return BOMB;
        } else if(tower.getTowerType() == WIZARD) {
            return ICE;
        }

        return -1;
    }
}
