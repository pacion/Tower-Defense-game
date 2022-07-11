package ui;

import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.*;

public class Toolbar extends Bar {
    private MyButton buttonMenu, buttonSave;
    private Editing editing;
    private Tile selectedTile;


    private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();
    private MyButton buttonGrass, buttonWater, buttonRoadCorner, buttonRoadStraight, buttonWaterCorner, buttonWaterBeaches, buttonWaterIslands;
    private MyButton currentButton;
    private int currentIndex = 0;


    public Toolbar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;


        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
        buttonSave = new MyButton("Save", 2, 674, 100, 30);

        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(width * 1.1f);

        int index = 0;

        buttonGrass = new MyButton("Grass", xStart, yStart, width, height, index++);
        buttonWater = new MyButton("Grass", xStart + xOffset, yStart, width, height, index++);

        initMapButton(buttonRoadStraight, editing.getGame().getTileManager().getRoadsStraight(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonRoadCorner, editing.getGame().getTileManager().getRoadsCorners(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterCorner, editing.getGame().getTileManager().getWaterCorners(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterBeaches, editing.getGame().getTileManager().getWaterBeaches(), xStart, yStart, xOffset, width, height, index++);
        initMapButton(buttonWaterIslands, editing.getGame().getTileManager().getWaterIslands(), xStart, yStart, xOffset, width, height, index++);
    }

    private void initMapButton(MyButton button, ArrayList<Tile> list, int x, int y, int xOff, int width, int height, int id) {
        button = new MyButton("", x + xOff * id, y, width, height, id);
        map.put(button, list);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(180,180,108));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonSave.draw(graphics);

        drawNormalButton(graphics, buttonGrass);
        drawNormalButton(graphics, buttonWater);
        drawSelectedTile(graphics);
        drawMapButtons(graphics);
    }

    private void drawNormalButton(Graphics graphics, MyButton button) {
        graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);

        drawButtonFeedback(graphics, button);
    }

    private void drawMapButtons(Graphics graphics) {
        for(Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton button = entry.getKey();
            BufferedImage image = entry.getValue().get(0).getSprite();

            graphics.drawImage(image, button.x, button.y, button.width, button.height, null);

            drawButtonFeedback(graphics, button);
        }
    }

    private void drawButtonFeedback(Graphics graphics, MyButton button) {
        if(button.isMouseOver())
            graphics.setColor(Color.white);
        else
            graphics.setColor(Color.black);

        graphics.drawRect(button.x, button.y, button.width, button.height);

        if(button.isMousePressed()) {
            graphics.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
            graphics.drawRect(button.x + 2, button.y + 2, button.width - 4, button.height - 4);
        }
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.black);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public BufferedImage getButtonImage(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if(buttonSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (buttonWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(buttonWater.getId());
        } else if (buttonGrass.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(buttonGrass.getId());
        } else {
            for(MyButton button : map.keySet()) {
                if(button.getBounds().contains(x, y)) {
                    selectedTile = map.get(button).get(0);
                    editing.setSelectedTile(selectedTile);
                    System.out.println(button);
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

        for(MyButton button : map.keySet()) {
            button.setMouseOver(false);
        }

        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setMouseOver(true);
        } else if(buttonWater.getBounds().contains(x, y)) {
            buttonWater.setMouseOver(true);
        } else if(buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setMouseOver(true);
        } else {
            for(MyButton button : map.keySet()) {
                if(button.getBounds().contains(x, y)) {
                    button.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMousePressed(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setMousePressed(true);
        } else if(buttonWater.getBounds().contains(x, y)) {
            buttonWater.setMousePressed(true);
        } else if(buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setMousePressed(true);
        } else {
            for(MyButton button : map.keySet()) {
                if(button.getBounds().contains(x, y)) {
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

        for(MyButton button : map.keySet()) {
            button.resetBooleans();
        }
    }

    public void rotateSprite() {
        currentIndex++;

        if(currentIndex >= map.get(currentButton).size())
            currentIndex = 0;

        selectedTile = map.get(currentButton).get(currentIndex);

        editing.setSelectedTile(selectedTile);
    }

}
