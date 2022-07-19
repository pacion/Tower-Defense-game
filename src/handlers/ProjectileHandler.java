package handlers;

import enemies.Enemy;
import helperMethods.Constants;
import helperMethods.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Projectiles.*;
import static helperMethods.Constants.Towers.*;

public class ProjectileHandler {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projectileImages;
    private BufferedImage[] explosionImages;
    private int projectileId = 0;
    private ArrayList<Explosion> explosions = new ArrayList<>();

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

        importExplosions(atlas);
    }

    private void importExplosions(BufferedImage atlas) {
        explosionImages = new BufferedImage[7];

        for(int i = 0; i < 7; i++) {
            explosionImages[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);
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

        if (type == ARROW) {
            float arcValue = (float) Math.atan(yDistance / (float) xDistance);
            rotation = (float) Math.toDegrees(arcValue);

            if (xDistance < 0)
                rotation += 180;
        }

        for (Projectile projectile : projectiles) {
            if (!projectile.isActive()) {
                if (projectile.getProjectileType() == type) {
                    projectile.reuse(tower.getX() + 16, tower.getY() + 16, xSpeed, ySpeed, tower.getDamage(), rotation);
                    return;
                }
            }
        }

        projectiles.add(new Projectile(tower.getX() + 16, tower.getY() + 16, xSpeed, ySpeed, tower.getDamage(), rotation, projectileId++, type));

    }

    public void update() {
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                projectile.move();

                if(isProjectileHittingEnemy(projectile)) {
                    projectile.setActive(false);

                    if(projectile.getProjectileType() == BOMB) {
                        explosions.add(new Explosion(projectile.getPosition()));
                        explodeOnEnemiesImpact(projectile);
                    }
                } else if(isProjectileOutsideBounds(projectile)) {
                    projectile.setActive(false);
                }
            }
        }

        for (Explosion explosion : explosions) {
            if (explosion.getIndex() < 7) {
                explosion.update();
            }
        }
    }

    private void explodeOnEnemiesImpact(Projectile projectile) {
        for (Enemy enemy : playing.getEnemyHandler().getEnemies()) {
            if (enemy.isAlive()) {
                float xDistance = Math.abs(projectile.getPosition().x - enemy.getX());
                float yDistance = Math.abs(projectile.getPosition().y - enemy.getY());
                float radiusOfExplosion = 40.0f;

                float realDistance = (float)Math.hypot(xDistance, yDistance);

                if (realDistance <= radiusOfExplosion)
                    enemy.hurt(projectile.getDamage());
            }

        }

    }

    private boolean isProjectileHittingEnemy(Projectile projectile) {
        for(Enemy enemy : playing.getEnemyHandler().getEnemies()) {
            if (enemy.isAlive())
                if (enemy.getBounds().contains(projectile.getPosition())) {
                    enemy.hurt(projectile.getDamage());

                    if (projectile.getProjectileType() == ICE) {
                        enemy.slow();
                    }

                    return true;
                }
        }

        return false;
    }

    private boolean isProjectileOutsideBounds(Projectile projectile) {
        if(projectile.getPosition().x >= 0)
            if(projectile.getPosition().x <= 640)
                if(projectile.getPosition().y >= 0)
                    if(projectile.getPosition().y <= 640)
                        return false;

        return true;
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

        drawExplosion(graphics2D);
    }

    private void drawExplosion(Graphics2D graphics2D) {
        for (Explosion explosion : explosions) {
            if (explosion.getIndex() < 7) {
                graphics2D.drawImage(explosionImages[explosion.getIndex()],
                        (int) explosion.getPos().x - 16,
                        (int) explosion.getPos().y - 16, null);
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

    public class Explosion {

        private Point2D.Float position;
        private int explosionTick, explosionIndex;

        public Explosion(Point2D.Float position) {
            this.position = position;
        }

        public void update() {
            explosionTick++;
            if (explosionTick >= 6) {
                explosionTick = 0;
                explosionIndex++;
            }
        }

        public int getIndex() {
            return explosionIndex;
        }

        public Point2D.Float getPos() {
            return position;
        }
    }

    public void reset() {
        projectiles.clear();
        explosions.clear();

        projectileId = 0;
    }
}
