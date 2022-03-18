package main;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Render {
    private Game game;

    public Render(Game game) {
        this.game = game;
    }

    public void render(Graphics graphics) {
        if(GameStates.gameState == GameStates.MENU) {
            game.getMenu().render(graphics);
        } else if(GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().render(graphics);
        } else if (GameStates.gameState == GameStates.SETTINGS) {
            game.getSettings().render(graphics);
        }
    }
}
