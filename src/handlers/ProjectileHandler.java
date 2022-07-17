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

        int xDistance = (int)(tower.getX() - enemy.getX());
        int yDistance = (int)(tower.getY() - enemy.getY());
        int sumOfDistances = Math.abs(xDistance) + Math.abs(yDistance);

        float xPercentage = (float)Math.abs(xDistance) / sumOfDistances;

        float xSpeed = xPercentage * GetSpeed(type);
        float ySpeed = GetSpeed(type) - xSpeed;

        if(tower.getX() > enemy.getX())
            xSpeed *= -1;

        if(tower.getY() > enemy.getY())
            ySpeed *= -1;

        float rotation = 0;

        if(type == ARROW ) {
            float arcusValue = (float) Math.atan(yDistance / (float) xDistance);
            rotation = (float) Math.toDegrees(arcusValue);

            if (xDistance < 0) {
                rotation += 180;
            }
        }

        projectiles.add(new Projectile(
                tower.getX() + 16,
                tower.getY() + 16, xSpeed, ySpeed, tower.getDamage(), rotation, projectileId++, type));
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
        Graphics2D graphics2D = (Graphics2D) graphics;

        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {

                if(projectile.getProjectileType() == ARROW) {
                    graphics2D.translate(projectile.getPosition().x, projectile.getPosition().y);
                    graphics2D.rotate(Math.toRadians(projectile.getRotation()));
                    graphics2D.drawImage(projectileImages[projectile.getProjectileType()], -16, -16, null);
                    graphics2D.rotate(-Math.toRadians(projectile.getRotation()));
                    graphics2D.translate(-projectile.getPosition().x, -projectile.getPosition().y);
                } else {
                    graphics2D.drawImage(projectileImages[projectile.getProjectileType()],
                            (int)projectile.getPosition().x - 16,
                            (int)projectile.getPosition().y - 16, null);
                }
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
