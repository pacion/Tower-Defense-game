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
    private MyButton buttonMenu;
    private Playing playing;
    private ArrayList<MyButton> tileButtons = new ArrayList<>();

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

        drawTileButtons(graphics);
    }

    private void drawTileButtons(Graphics graphics) {
        for(MyButton button : tileButtons) {
            graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
        }
    }

    public BufferedImage getButtonImage(int id) {
        return playing.getTileHandler().getSprite(id);
    }


    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y))
            SetGameState(MENU);
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        if (buttonMenu.getBounds().contains(x, y))
            buttonMenu.setMouseOver(true);
    }

    public void mousePressed(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y))
            buttonMenu.setMousePressed(true);
    }

    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
    }
}
