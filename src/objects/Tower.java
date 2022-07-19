package objects;

import static helperMethods.Constants.Towers.*;

public class Tower {
    private int x, y, id, towerType;
    private float  range, cooldown;
    private int cooldownTick;
    private int damage;
    private int tier;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    public void update() {
        cooldownTick++;
    }

    public void upgradeTower() {
        this.tier++;

        if(towerType == ARCHER) {
            damage += 2;
            range += 10;
            cooldown -= 5;
        } else if(towerType == CANNON) {
            damage += 2;
            range += 7;
            cooldown -= 15;
        } else if(towerType == WIZARD) {
            damage = 1;
            range += 15;
            cooldown -= 12;
        }
    }

    public boolean isCooldownOver() {
        return cooldownTick >= cooldown;
    }

    public void resetCooldown() {
        cooldownTick = 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getTier() {
        return tier;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    private void setDefaultCooldown() {
        cooldown = GetDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = GetDefaultRange(towerType);
    }

    private void setDefaultDamage() {
        damage = GetStartDamage(towerType);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }
}
