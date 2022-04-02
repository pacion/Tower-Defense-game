package helperMethods;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFix {
    public static BufferedImage getRotatedImage(BufferedImage image, int angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());

        Graphics2D graphics2D = rotatedImage.createGraphics();

        graphics2D.rotate(Math.toRadians(angle), width / 2.f, height / 2.f);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return rotatedImage;
    }

    public static BufferedImage buildImage(BufferedImage[] images) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());

        Graphics2D graphics2D = newImage.createGraphics();

        for(BufferedImage image : images) {
            graphics2D.drawImage(image, 0, 0, null);
        }

        graphics2D.dispose();

        return newImage;
    }
}
