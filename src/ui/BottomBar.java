package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class BottomBar {
    private int x, y, width, height;
    private MyButton buttonMenu, buttonSave;
    private Playing playing;
    private ArrayList<MyButton> tileButtons = new ArrayList<>();
    private Tile selectedTile;

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;

        initButtons();
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(180,180,108));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
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
        for(Tile tile : playing.getTileHandler().tiles) {
            tileButtons.add(new MyButton(tile.getName(), xStart + xOffset * index, yStart, w, h, index++));
        }
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
                graphics.setColor(Color.black  );

            graphics.drawRect(button.x, button.y, button.width, button.height);

            if(button.isMousePressed()) {
                graphics.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
                graphics.drawRect(button.x + 2, button.y + 2, button.width - 4, button.height - 4);
            }
        }
    }

    public BufferedImage getButtonImage(int id) {
        return playing.getTileHandler().getSprite(id);
    }


    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if(buttonSave.getBounds().contains(x, y)) {
            saveLevel();
        } else {
            for(MyButton button : tileButtons) {
                if(button.getBounds().contains(x, y)) {
                    selectedTile = playing.getTileHandler().getTile(button.getId());
                    playing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        playing.saveLevel();
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
