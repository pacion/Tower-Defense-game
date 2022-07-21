package main;

import java.awt.*;

public class Render {
    private Game game;

    public Render(Game game) {
        this.game = game;
    }

    public void render(Graphics graphics) {
        if (GameStates.gameState == GameStates.MENU) {
            game.getMenu().render(graphics);
        } else if (GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().render(graphics);
        } else if (GameStates.gameState == GameStates.SETTINGS) {
            game.getSettings().render(graphics);
        } else if (GameStates.gameState == GameStates.EDIT) {
            game.getEditor().render(graphics);
        } else if (GameStates.gameState == GameStates.GAME_OVER) {
            game.getGameOver().render(graphics);
        }
    }
}
