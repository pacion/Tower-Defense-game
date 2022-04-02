package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;

import scenes.Playing;

public class ActionBar extends Bar {
    private Playing playing;
    private MyButton buttonMenu;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
    }

    private void drawButtons(Graphics g) {
        buttonMenu.draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        drawButtons(g);
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
