package ui;

import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.*;

public class Toolbar extends Bar {
    private MyButton buttonMenu, buttonSave;
    private Editing editing;
    private Tile selectedTile;
    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public Toolbar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
        buttonSave = new MyButton("Save", 2, 674, 100, 30);

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(w * 1.1f);

        int index = 0;
        for(Tile tile : editing.getGame().getTileManager().tiles) {
            tileButtons.add(new MyButton(tile.getName(), xStart + xOffset * index, yStart, w, h, index++));
        }
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(180,180,108));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonSave.draw(graphics);

        drawTileButtons(graphics);
        drawSelectedTile(graphics);
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.black);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    private void drawTileButtons(Graphics graphics) {
        for(MyButton button : tileButtons) {
            graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);

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
        } else {
            for(MyButton button : tileButtons) {
                if(button.getBounds().contains(x, y)) {
                    selectedTile = editing.getGame().getTileManager().getTile(button.getId());
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        buttonSave.setMouseOver(false);

        for(MyButton button : tileButtons)
            button.setMouseOver(false);

        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setMouseOver(true);
        } else {
            for(MyButton button : tileButtons) {
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
        } else {
            for(MyButton button : tileButtons) {
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

        for(MyButton button : tileButtons) {
            button.resetBooleans();
        }
    }

}
