package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.*;
import java.text.DecimalFormat;

import helperMethods.Constants;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {
    private Playing playing;
    private MyButton buttonMenu;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

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

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(51, 153, 110));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
        drawDisplayedTower(graphics);
        drawWaveInformation(graphics);
    }

    private void drawWaveInformation(Graphics graphics) {
       //if(playing.getWaveHandler().isWaveTimerStarted()) {
           drawWaveTimerInformation(graphics);
            drawEnemiesLeftInformation(graphics);
            drawWavesLeftInformation(graphics);//}
    }

    private void drawEnemiesLeftInformation(Graphics graphics) {
        int current = playing.getWaveHandler().getWaveIndex();
        int size = playing.getWaveHandler().getWaves().size();
        graphics.drawString("Wave: " + (current + 1) + " / " + size, 425,720);
    }

    private void drawWavesLeftInformation(Graphics graphics) {
        int remaining = playing.getEnemyHandler().getAmountOfAliveEnemies();
        graphics.drawString("Enemies left: " + remaining, 425, 690);
    }


    private void drawWaveTimerInformation(Graphics graphics) {
        float timeLeft = playing.getWaveHandler().getTimeLeft();
        String formatedText = formatter.format(timeLeft);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Monaco", Font.BOLD, 20));
        graphics.drawString("Time Left: " + formatedText, 425, 660);
    }

    private void drawDisplayedTower(Graphics graphics) {
        if(displayedTower != null) {
            graphics.setColor(new Color(159, 204, 53));
            graphics.fillRect(410, 645, 220, 85);
            graphics.setColor(Color.black);
            graphics.drawRect(410, 645, 220, 85);
            graphics.drawRect(420, 650, 50, 50);
            graphics.drawImage(playing.getTowerHandler().getTowerImages()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
            graphics.setFont(new Font("Monaco", Font.BOLD, 16));
            graphics.drawString("" + Constants.Towers.getName(displayedTower.getTowerType()), 490, 660);
            graphics.drawString("ID: " + displayedTower.getId(), 490, 675);

            drawDisplayedTowerBorder(graphics);
            drawDisplayedTowerRange(graphics);
        }
    }

    private void drawDisplayedTowerRange(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawOval(displayedTower.getX() + 16 - (int)(displayedTower.getRange() * 2) / 2,
                displayedTower.getY() + 16 - (int)(displayedTower.getRange() * 2) / 2,
                (int)displayedTower.getRange() * 2,
                (int) displayedTower.getRange() * 2);

    }

    private void drawDisplayedTowerBorder(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawRect(displayedTower.getX(), displayedTower.getY(),32, 32);
    }

    public void displayTower(Tower tower) {
        displayedTower = tower;
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
