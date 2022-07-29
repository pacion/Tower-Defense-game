package ui;

import helperMethods.Constants;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static main.GameStates.*;

public class ActionBar extends Bar {
    private final Playing playing;
    private MyButton buttonMenu, buttonPause;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private final DecimalFormat formatter;
    private int gold = 110;
    private boolean showTowerCost;
    private int towerCostType;
    private MyButton sellTower, upgradeTower;
    private int hearts = 5;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
        buttonPause = new MyButton("Pause", 2, 682, 100, 30);

        towerButtons = new MyButton[3];
        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, width, height, i);
        }

        sellTower = new MyButton("Sell", 420, 702, 80, 25);
        upgradeTower = new MyButton("Upgrade", 545, 702, 80, 25);

    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonPause.draw(graphics);

        for (MyButton button : towerButtons) {
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

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Monaco", Font.BOLD, 20));

        drawWaveInformation(graphics);
        drawGoldAmount(graphics);

        if (showTowerCost) {
            drawTowerCost(graphics);
        }

        if (playing.isGamePaused()) {
            graphics.drawString("Game is paused!", 110, 790);
        }

        graphics.drawString("Hearts: " + hearts, 110, 750);
    }

    private void drawTowerCost(Graphics graphics) {
        graphics.setColor(new Color(159, 204, 53));
        graphics.fillRect(280, 650, 120, 50);
        graphics.setColor(Color.black);
        graphics.drawRect(280, 650, 120, 50);
        graphics.drawString("" + getTowerCostName(), 285, 670);
        graphics.drawString("Cost: " + getTowerCostGold() + "g", 285, 695);

        if (isTowerCostMoreThanCurrentGold()) {
            graphics.setFont(new Font("Monaco", Font.BOLD, 20));
            graphics.drawString("Can't afford!", 285, 725);
        }
    }

    private boolean isTowerCostMoreThanCurrentGold() {
        return getTowerCostGold() > gold;
    }

    private int getTowerCostGold() {
        return Constants.Towers.GetTowerCost(towerCostType);
    }

    private String getTowerCostName() {
        return Constants.Towers.GetName(towerCostType);
    }

    private void drawGoldAmount(Graphics graphics) {
        graphics.drawString("Gold: " + gold, 110, 725);
    }

    private void drawWaveInformation(Graphics graphics) {
        if (playing.getWaveHandler().isWaveTimerStarted()) {
            drawWaveTimerInformation(graphics);
        }
        drawEnemiesLeftInformation(graphics);
        drawWavesLeftInformation(graphics);
    }

    private void drawEnemiesLeftInformation(Graphics graphics) {
        int current = playing.getWaveHandler().getWaveIndex();
        int size = playing.getWaveHandler().getWaves().size();
        graphics.drawString("Wave: " + (current + 1) + " / " + size, 425, 770);
    }

    private void drawWavesLeftInformation(Graphics graphics) {
        int remaining = playing.getEnemyHandler().getAmountOfAliveEnemies();
        graphics.drawString("Enemies left: " + remaining, 425, 790);
    }


    private void drawWaveTimerInformation(Graphics graphics) {
        float timeLeft = playing.getWaveHandler().getTimeLeft();
        String formatedText = formatter.format(timeLeft);
        graphics.drawString("Time Left: " + formatedText, 425, 750);
    }

    private void drawDisplayedTower(Graphics graphics) {
        if (displayedTower != null) {
            graphics.setColor(new Color(159, 204, 53));
            graphics.fillRect(410, 645, 220, 85);
            graphics.setColor(Color.black);
            graphics.drawRect(410, 645, 220, 85);
            graphics.drawRect(420, 650, 50, 50);
            graphics.drawImage(playing.getTowerHandler().getTowerImages()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
            graphics.setFont(new Font("Monaco", Font.BOLD, 16));
            graphics.drawString("" + Constants.Towers.GetName(displayedTower.getTowerType()), 480, 660);
            graphics.drawString("ID: " + displayedTower.getId(), 480, 675);
            graphics.drawString("Tier: " + displayedTower.getTier(), 560, 660);

            drawDisplayedTowerBorder(graphics);
            drawDisplayedTowerRange(graphics);

            sellTower.draw(graphics);

            if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower))
                upgradeTower.draw(graphics);

            if (sellTower.isMouseOver()) {
                graphics.setColor(new Color(255, 0, 0));
                graphics.drawString("Sell for: " + getSellAmount(displayedTower) + "g", 480, 696);
            } else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
                graphics.setColor(new Color(14, 26, 246));
                graphics.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + "g", 480, 696);
            }
        }
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 3;
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost /= 3;

        return Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private void drawDisplayedTowerRange(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                (int) displayedTower.getRange() * 2,
                (int) displayedTower.getRange() * 2);

    }

    private void drawDisplayedTowerBorder(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    public void displayTower(Tower tower) {
        displayedTower = tower;
    }

    public void mouseClicked(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (buttonPause.getBounds().contains(x, y)) {
            togglePause();
        } else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                    upgradeTowerClicked();
                    return;
                }
            }

            for (MyButton button : towerButtons) {
                if (button.getBounds().contains(x, y)) {
                    if (isGoldEnoughForTower(button.getId())) {
                        selectedTower = new Tower(0, 0, -1, button.getId());
                        playing.setSelectedTower(selectedTower);
                        return;
                    }
                }
            }
        }
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused()) {
            buttonPause.setText("Unpause");
        } else {
            buttonPause.setText("Pause");
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);

        gold += getSellAmount(displayedTower);
        displayedTower = null;
    }

    private void gradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private boolean isGoldEnoughForTower(int towerType) {
        return gold >= Constants.Towers.GetTowerCost(towerType);
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setMouseOver(false);
        buttonPause.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);

        for (MyButton button : towerButtons) {
            button.setMouseOver(false);
        }

        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMouseOver(true);
        } else if (buttonPause.getBounds().contains(x, y)) {
            buttonPause.setMouseOver(true);
        } else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }

            for (MyButton button : towerButtons) {
                if (button.getBounds().contains(x, y)) {
                    button.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = button.getId();
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setMousePressed(true);
        } else if (buttonPause.getBounds().contains(x, y)) {
            buttonPause.setMousePressed(true);
        } else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y)) {
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }

            for (MyButton button : towerButtons) {
                if (button.getBounds().contains(x, y)) {
                    button.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        buttonPause.resetBooleans();

        for (MyButton button : towerButtons) {
            button.resetBooleans();
        }
    }

    public void payForTower(int towerType) {
        this.gold -= Constants.Towers.GetTowerCost(towerType);
    }

    public void removeOneHeart() {
        hearts--;

        if (hearts <= 0) {
            SetGameState(GAME_OVER);
        }
    }

    public int getHearts() {
        return hearts;
    }

    public void addGold(int getReward) {
        this.gold += getReward;
    }

    public void resetEverything() {
        hearts = 5;
        towerCostType = 0;
        showTowerCost = false;
        gold = 110;
        selectedTower = null;
        displayedTower = null;
    }
}
