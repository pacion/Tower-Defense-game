package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;

import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {
    private Playing playing;
    private MyButton buttonMenu;
    private MyButton[] towerButtons;
    private Tower selectedTower;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);

        towerButtons = new MyButton[3];
        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(width * 1.1f);

        for(int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, width, height, i);
        }
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);

        for(MyButton button: towerButtons) {
            graphics.setColor(new Color(159, 204, 53));
            graphics.fillRect(button.x, button.y, button.width, button.height);
            graphics.drawImage(playing.getTowerHandler().getTowerImages()[button.getId()], button.x, button.y, button.width, button.height, null);
            drawButtonFeedback(graphics, button);
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(51, 153, 110));
        g.fillRect(x, y, width, height);

        drawButtons(g);
    }

    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else {
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0,-1, button.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);

        for(MyButton button: towerButtons) {
            button.setMouseOver(false);
        }

        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else {
            for(MyButton button: towerButtons) {
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
        } else {
            for(MyButton button: towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    button.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
        for(MyButton button: towerButtons) {
            button.resetBooleans();
        }
    }
}
