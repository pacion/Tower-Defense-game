package handlers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Towers.ARCHER;
import static helperMethods.Utils.GetHypotDistance;

public class TowerHandler {
    private final Playing playing;
    private BufferedImage[] towerImages;
    private final ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    public TowerHandler(Playing playing) {
        this.playing = playing;

        loadTowerImages();
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            towerImages[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
        }
    }

    public void draw(Graphics graphics) {
        for (Tower tower : towers) {
            graphics.drawImage(towerImages[tower.getTowerType()], tower.getX(), tower.getY(), null);
        }
    }

    public void addTower(Tower selectedTower, int x, int y) {
        towers.add(new Tower(x, y, towerAmount++, selectedTower.getTowerType()));
    }

    public void update() {
        for (Tower tower : towers) {
            tower.update();
            attackEnemyIfInRange(tower);
        }
    }

    private void attackEnemyIfInRange(Tower tower) {
        for (Enemy enemy : playing.getEnemyHandler().getEnemies()) {
            if (enemy.isAlive()) {
                if (isEnemyInRange(tower, enemy)) {
                    if (tower.isCooldownOver()) {
                        playing.shootEnemy(tower, enemy);
                        tower.resetCooldown();
                    }
                }
            }
        }
    }

    private boolean isEnemyInRange(Tower tower, Enemy enemy) {
        int range = GetHypotDistance(tower.getX(), tower.getY(), enemy.getX(), enemy.getY());

        return range < tower.getRange();
    }

    public void upgradeTower(Tower displayedTower) {
        Tower matched = towers.stream()
                .filter(tower -> tower.getId() == displayedTower.getId())
                .findFirst()
                .orElse(null);

        assert matched != null;
        matched.upgradeTower();
    }

    public BufferedImage[] getTowerImages() {
        return towerImages;
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower tower : towers) {
            if (tower.getX() == x && tower.getY() == y) {
                return tower;
            }
        }

        return null;
    }

    public void removeTower(Tower displayedTower) {
        towers.removeIf(tower -> tower.getId() == displayedTower.getId());
    }

    public void reset() {
        towers.clear();
        towerAmount = 0;
    }
}
