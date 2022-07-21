package handlers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Tiles.*;

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

        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE));
        tiles.add(WATER = new Tile(getAnimationSprites(0, 0), id++, WATER_TILE));

        roadsStraight.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE));
        roadsStraight.add(ROAD_TB = new Tile(ImageFix.getRotatedImage(getSprite(8, 0), 90), id++, ROAD_TILE));

        roadsCorners.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
        roadsCorners.add(ROAD_L_TO_B = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 90), id++, ROAD_TILE));
        roadsCorners.add(ROAD_L_TO_T = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 180), id++, ROAD_TILE));
        roadsCorners.add(ROAD_T_TO_R = new Tile(ImageFix.getRotatedImage(getSprite(7, 0), 270), id++, ROAD_TILE));

        waterCorners.add(BL_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 0), id++, WATER_TILE));
        waterCorners.add(TL_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 90), id++, WATER_TILE));
        waterCorners.add(TR_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 180), id++, WATER_TILE));
        waterCorners.add(BR_WATER_CORNER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(5, 0), 270), id++, WATER_TILE));

        waterBeaches.add(T_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 0), id++, WATER_TILE));
        waterBeaches.add(R_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 90), id++, WATER_TILE));
        waterBeaches.add(B_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 180), id++, WATER_TILE));
        waterBeaches.add(L_WATER = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(6, 0), 270), id++, WATER_TILE));

        waterIslands.add(TL_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
        waterIslands.add(TR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
        waterIslands.add(BR_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
        waterIslands.add(BL_ISLE = new Tile(ImageFix.getBuildRotatedImage(getAnimationSprites(0, 0), getSprite(4, 0), 270), id, WATER_TILE));

        tiles.addAll(roadsStraight);
        tiles.addAll(roadsCorners);
        tiles.addAll(waterCorners);
        tiles.addAll(waterBeaches);
        tiles.addAll(waterIslands);
    }

    public boolean isSpriteAnimation(int spriteId) {
        return tiles.get(spriteId).isAnimation();
    }

    /*private BufferedImage[] getImages(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[]{getSprite(firstX, firstY), getSprite(secondX, secondY)};
    }*/

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

        for (int i = 0; i < 4; i++) {
            sprites[i] = getSprite(x + i, y);
        }

        return sprites;
    }

    public int[][] getTypeArray() {
        int[][] idArray = LoadSave.GetLevelData();
        assert idArray != null;
        int[][] typeArray = new int[idArray.length][idArray[0].length];

        for (int i = 0; i < idArray.length; i++) {
            for (int j = 0; j < idArray[i].length; j++) {
                int id = idArray[i][j];
                typeArray[i][j] = tiles.get(id).getTileType();
            }
        }

        return typeArray;
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
