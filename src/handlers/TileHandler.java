package handlers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileHandler {
    public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER,
            TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER, L_WATER, TL_ISLE, TR_ISLE,
            BR_ISLE, BL_ISLE;

    /*
        T = top
        B = bottom
        L = left
        R = right
     */

    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsStraight = new ArrayList<>();
    public ArrayList<Tile> roadsCorners = new ArrayList<>();
    public ArrayList<Tile> waterCorners = new ArrayList<>();
    public ArrayList<Tile> waterBeaches = new ArrayList<>();
    public ArrayList<Tile> waterIslands = new ArrayList<>();

    public TileHandler() {
        loadAtlas();
        createTiles();
    }

    private void createTiles() {
        int id = 0;

        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, "Grass"));
        tiles.add(WATER = new Tile(getAnimationSprites(0, 0), id++, "Water"));

        roadsStraight.add(ROAD_LR = new Tile(getSprite(8, 0), id++, "Road_LR"));
        roadsStraight.add(ROAD_TB = new Tile(ImageFix.getRotatedImage(getSprite(8, 0), 90), id++, "TB_Road"));

		roadsCorners.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, "Road_Bottom_To_Right"));
        roadsCorners.add(ROAD_L_TO_B = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 90), id++, "Road_Left_To_Bottom"));
        roadsCorners.add(ROAD_L_TO_T = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 180), id++, "Road_Left_To_Top"));
        roadsCorners.add(ROAD_T_TO_R = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 270), id++, "Road_Top_To_Right"));

        waterCorners.add(BL_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 0), id++, "BL_Corner"));
        waterCorners.add(TL_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 90), id++, "TL_Corner"));
        waterCorners.add(TR_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 180), id++, "TR_Corner"));
        waterCorners.add(BR_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 270), id++, "BR_Corner"));

<<<<<<< HEAD
        waterBeaches.add(T_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 0), id++, "T_Water"));
        waterBeaches.add(R_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 90), id++, "R_Water"));
        waterBeaches.add(B_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 180), id++, "B_Water"));
        waterBeaches.add(L_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 270), id++, "L_Water"));

        waterIslands.add(TL_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 0), id++, "TL_Isle"));
        waterIslands.add(TR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 90), id++, "TR_Isle"));
        waterIslands.add(BR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 180), id++, "BR_Isle"));
        waterIslands.add(BL_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 270), id++, "BL_Isle"));
=======
        waterBeaches.add(T_WATER = new Tile(ImageFix.buildImage(getImages(0, 0, 6, 0)), id++, "T_Water"));
		waterBeaches.add(R_WATER = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 6, 0), 90, 1), id++, "R_Water"));
		waterBeaches.add(B_WATER = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 6, 0), 180, 1), id++, "B_Water"));
		waterBeaches.add(L_WATER = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 6, 0), 270, 1), id++, "L_Water"));

		waterIslands.add(TL_ISLE = new Tile(ImageFix.buildImage(getImages(0, 0, 4, 0)), id++, "TL_Isle"));
        waterIslands.add(TR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 4, 0), 90, 1), id++, "TR_Isle"));
        waterIslands.add(BR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 4, 0), 180, 1), id++, "BR_Isle"));
        waterIslands.add(BL_ISLE = new Tile(ImageFix.getBuildRotatedImage(getImages(0, 0, 4, 0), 270, 1), id++, "BL_Isle"));
>>>>>>> parent of dad306a (Rotation of squares.)

        tiles.addAll(roadsStraight);
        tiles.addAll(roadsCorners);
        tiles.addAll(waterCorners);
        tiles.addAll(waterBeaches);
        tiles.addAll(waterIslands);
    }

    public boolean isSpriteAnimation(int spriteId) {
        return tiles.get(spriteId).isAnimation();
    }

    private BufferedImage[] getImages(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[]{getSprite(firstX, firstY), getSprite(secondX, secondY)};
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    public BufferedImage getAnimationSprite(int id, int animationIndex) {
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAnimationSprites(int x, int y) {
        BufferedImage[] sprites = new BufferedImage[4];

        for(int i = 0; i < 4; i++) {
            sprites[i] = getSprite(x + i, y);
        }

        return sprites;
    }

    private BufferedImage getSprite(int x, int y) {
        return atlas.getSubimage(x * 32, y * 32, 32, 32);
    }

    public ArrayList<Tile> getRoadsStraight() {
        return roadsStraight;
    }

    public ArrayList<Tile> getRoadsCorners() {
        return roadsCorners;
    }

    public ArrayList<Tile> getWaterCorners() {
        return waterCorners;
    }

    public ArrayList<Tile> getWaterBeaches() {
        return waterBeaches;
    }

    public ArrayList<Tile> getWaterIslands() {
        return waterIslands;
    }
}
