package scenes;

import helperMethods.LoadSave;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods{
    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private Random random;

    public Menu(Game game) {
        super(game);
        random = new Random();
        importImage();
        loadSprites();
    }

    @Override
    public void render(Graphics graphics) {
        for (int y = 0; y < 20; ++y)
            for (int x = 0; x < 20; ++x)
                graphics.drawImage(sprites.get(random.nextInt(30)), x * 32, y * 32, null);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/res/spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException event) {
            event.printStackTrace();
        }
    }

    private void loadSprites() {
        int rowsOfSpriteatlas = 3;
        int colsOfSpriteatlas = 10;

        for(int y = 0; y < rowsOfSpriteatlas; ++y)
            for(int x = 0; x < colsOfSpriteatlas; ++x)
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
    }
}
