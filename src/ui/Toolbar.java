package ui;

import helperMethods.LoadSave;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class Toolbar extends Bar {
    private MyButton buttonMenu, buttonSave;
    private MyButton buttonPathStart, buttonPathEnd;
    private BufferedImage pathStart, pathEnd;
    private final Editing editing;
    private Tile selectedTile;


    private final Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();
    private MyButton buttonGrass, buttonWater, buttonRoadCorner, buttonRoadStraight, buttonWaterCorner, buttonWaterBeaches, buttonWaterIslands;
    private MyButton currentButton;
    private int currentIndex = 0;


    public Toolbar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;

        initPathImages();
        initButtons();
    }

    private void initPathImages() {
        pathStart = LoadSave.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
        pathEnd = LoadSave.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
        buttonSave = new MyButton("Save", 2, 674, 100, 30);

        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);

        int index = 0;

        buttonGrass = new MyButton("Grass", xStart, yStart, width, height, index++);
        buttonWater = new MyButton("Grass", xStart + xOffset, yStart, width, height, index++);

        initMapButton(buttonRoadStraight, editing.getGame().getTileHandler().getRoadsStraight(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonRoadCorner, editing.getGame().getTileHandler().getRoadsCorners(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterCorner, editing.getGame().getTileHandler().getWaterCorners(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterBeaches, editing.getGame().getTileHandler().getWaterBeaches(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterIslands, editing.getGame().getTileHandler().getWaterIslands(), xStart, yStart, xOffset, width, height, index++);

        buttonPathStart = new MyButton("PathStart", xStart, yStart + xOffset, width, height, index++);
        buttonPathEnd = new MyButton("PathEnd", xStart + xOffset, yStart + xOffset, width, height, index++);
    }

    private void initMapButton(MyButton button, ArrayList<Tile> list, int x, int y, int xOff, int width, int height, int id) {
        button = new MyButton("", x + xOff * id, y, width, height, id);
        map.put(button, list);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(1, 93, 146, 228));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonSave.draw(graphics);

        drawPathButton(graphics, buttonPathStart, pathStart);
        drawPathButton(graphics, buttonPathEnd, pathEnd);

        drawNormalButton(graphics, buttonGrass);
        drawNormalButton(graphics, buttonWater);
        drawSelectedTile(graphics);
        drawMapButtons(graphics);
    }

    private void drawPathButton(Graphics graphics, MyButton button, BufferedImage image) {
        graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
        drawButtonFeedback(graphics, button);
    }

    private void drawNormalButton(Graphics graphics, MyButton button) {
        graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);

        drawButtonFeedback(graphics, button);
    }

    private void drawMapButtons(Graphics graphics) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton button = entry.getKey();
            BufferedImage image = entry.getValue().get(0).getSprite();

            graphics.drawImage(image, button.x, button.y, button.width, button.height, null);

            drawButtonFeedback(graphics, button);
        }
    }

    private void drawSelectedTile(Graphics graphics) {
        if (selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.black);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public BufferedImage getButtonImage(int id) {
        return editing.getGame().getTileHandler().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        currentButton = null;

        if (buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (buttonSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (buttonWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileHandler().getTile(buttonWater.getId());
            editing.setSelectedTile(selectedTile);
            return;
        } else if (buttonGrass.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileHandler().getTile(buttonGrass.getId());
            editing.setSelectedTile(selectedTile);
            return;
        } else if (buttonPathStart.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathStart, -1, -1);
            editing.setSelectedTile(selectedTile);
        } else if (buttonPathEnd.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathEnd, -2, -2);
            editing.setSelectedTile(selectedTile);
        } else {
            for (MyButton button : map.keySet()) {
                if (button.getBounds().contains(x, y)) {
                    selectedTile = map.get(button).get(0);
                    editing.setSelectedTile(selectedTile);
                    currentButton = button;
                    currentIndex = 0;
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        buttonSave.setMouseOver(false);
        buttonWater.setMouseOver(false);
        buttonGrass.setMouseOver(false);
        buttonPathStart.setMouseOver(false);
        buttonPathEnd.setMouseOver(false);

        for (MyButton button : map.keySet()) {
            button.setMouseOver(false);
        }

        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else if (buttonSave.getBounds().contains(x, y)) {
            buttonSave.setMouseOver(true);
        } else if (buttonWater.getBounds().contains(x, y)) {
            buttonWater.setMouseOver(true);
        } else if (buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setMouseOver(true);
        } else if (buttonPathStart.getBounds().contains(x, y)) {
            buttonPathStart.setMouseOver(true);
        } else if (buttonPathEnd.getBounds().contains(x, y)) {
            buttonPathEnd.setMouseOver(true);
        } else {
            for (MyButton button : map.keySet()) {
                if (button.getBounds().contains(x, y)) {
                    button.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMousePressed(true);
        } else if (buttonSave.getBounds().contains(x, y)) {
            buttonSave.setMousePressed(true);
        } else if (buttonWater.getBounds().contains(x, y)) {
            buttonWater.setMousePressed(true);
        } else if (buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setMousePressed(true);
        } else {
            for (MyButton button : map.keySet()) {
                if (button.getBounds().contains(x, y)) {
                    button.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
        buttonSave.resetBooleans();
        buttonGrass.resetBooleans();
        buttonWater.resetBooleans();

        for (MyButton button : map.keySet()) {
            button.resetBooleans();
        }
    }

    public void rotateSprite() {
        if (selectedTile == null || currentButton == null)
            return;

        currentIndex++;

        if (currentIndex >= map.get(currentButton).size())
            currentIndex = 0;

        selectedTile = map.get(currentButton).get(currentIndex);

        editing.setSelectedTile(selectedTile);
    }

    public BufferedImage getStartPathImage() {
        return pathStart;
    }

    public BufferedImage getEndPathImage() {
        return pathEnd;
    }

}
