package scenes;

import main.Game;

import java.awt.image.BufferedImage;

public class GameScene {
    protected Game game;
    protected int animationIndex;
    protected int tickRate;
    private int ANIMATION_SPEED = 25;

    public GameScene(Game game) {
        this.game = game;
    }

    protected void updateTick() {
        tickRate++;

        if (tickRate >= ANIMATION_SPEED) {
            tickRate = 0;
            animationIndex++;

            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }

    protected boolean isAnimation(int spriteId) {
        return getGame().getTileHandler().isSpriteAnimation(spriteId);
    }

    protected BufferedImage getSprite(int spriteId, int animationIndex) {
        return getGame().getTileHandler().getAnimationSprite(spriteId, animationIndex);
    }

    protected BufferedImage getSprite(int spriteId) {
        return getGame().getTileHandler().getSprite(spriteId);
    }


    public Game getGame() {
        return game;
    }
}
