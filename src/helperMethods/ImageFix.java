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

        for (BufferedImage image : images) {
            graphics2D.drawImage(image, 0, 0, null);
        }

        graphics2D.dispose();

        return newImage;
    }

    public static BufferedImage getBuildRotatedImage(BufferedImage[] images, int angle, int indexOfImageToRotate) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());

        Graphics2D graphics2D = newImage.createGraphics();

        for (int i = 0; i < images.length; ++i) {
            if (indexOfImageToRotate == i) {
                graphics2D.rotate(Math.toRadians(angle), width / 2.f, height / 2.f);
            }

            graphics2D.drawImage(images[i], 0, 0, null);

            if (indexOfImageToRotate == i) {
                graphics2D.rotate(Math.toRadians(-angle), width / 2.f, height / 2.f);
            }
        }

        graphics2D.dispose();

        return newImage;
    }

    public static BufferedImage[] getBuildRotatedImage(BufferedImage[] images, BufferedImage secondImage, int angle) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage[] arr = new BufferedImage[images.length]; // VLA because Tile constructor takes an array

        for (int i = 0; i < images.length; i++) {
            BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
            Graphics2D graphics2D = newImage.createGraphics();

            graphics2D.drawImage(images[i], 0, 0, null);
            graphics2D.rotate(Math.toRadians(angle), width / 2.f, height / 2.f);
            graphics2D.drawImage(secondImage, 0, 0, null);
            graphics2D.dispose();

            arr[i] = newImage;
        }

        return arr;
    }
}
