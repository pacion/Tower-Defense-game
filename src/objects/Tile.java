package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage[] sprite;
    private int tileType;
    private int id;

    public Tile(BufferedImage sprite, int id, int tileType) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    public Tile(BufferedImage[] sprite, int id, int tileType) {
        this.sprite = sprite;
        this.id = id;
        this.tileType = tileType;
    }

    public boolean isAnimation() {
        return sprite.length > 1;
    }

    public BufferedImage getSprite() {
        return getSprite(0);
    }

    public BufferedImage getSprite(int animationIndex) {
        return sprite[animationIndex];
    }

    public int getId() {
        return id;
    }

    public int getTileType() {
        return tileType;
    }
}
