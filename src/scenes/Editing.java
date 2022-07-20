package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helperMethods.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.Toolbar;

import static helperMethods.Constants.Tiles.ROAD_TILE;

public class Editing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelect;
    private PathPoint start, end;

    private Toolbar toolbar;

    public Editing(Game game) {
        super(game);

        loadDefaultLevel();

        toolbar = new Toolbar(0, 640, 640, 160, this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.GetLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");
        start = points.get(0);
        end = points.get(1);
    }

    @Override
    public void render(Graphics graphics) {
        updateTick();

        drawLevel(graphics);
        toolbar.draw(graphics);
        drawSelectedTile(graphics);
        drawPathPoints(graphics);
    }

    private void drawPathPoints(Graphics graphics) {
        if(start != null) {
            graphics.drawImage(toolbar.getStartPathImage(), start.getX() * 32, start.getY() * 32, 32, 32, null);
        }

        if(end != null) {
            graphics.drawImage(toolbar.getEndPathImage(), end.getX() * 32, end.getY() * 32, 32, 32, null);

        }
    }

    private void drawLevel(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];

                if(isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawSelect) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void update() {
        updateTick();
    }

    public void saveLevel() {
        LoadSave.SaveLevel("new_level", lvl, start, end);
        getGame().getPlaying().setLevel(lvl);
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
    }

    private void changeTile(int x, int y) {
        if (selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;

            if(selectedTile.getId() >= 0) {
                if (lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId())
                    return;

                if (x <= 0 || y <= 0 || x >= 640 || y >= 740)
                    return;

                lastTileX = tileX;
                lastTileY = tileY;
                lastTileId = selectedTile.getId();

                lvl[tileY][tileX] = selectedTile.getId();
            } else {
                int id = lvl[tileY][tileX];

                if(game.getTileHandler().getTile(id).getTileType() == ROAD_TILE) {
                    if(selectedTile.getId() == -1) {
                        start = new PathPoint(tileX, tileY);
                    } else if (selectedTile.getId() == -2) {
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            toolbar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }

    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            toolbar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }

    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            toolbar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        toolbar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y >= 640) {

        } else {
            changeTile(x, y);
        }

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            toolbar.rotateSprite();
        }
    }

}
