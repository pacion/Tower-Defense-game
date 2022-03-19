package helperMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("./res/spriteatlas.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException event) {
            event.printStackTrace();
        }

        return image;
    }
}
