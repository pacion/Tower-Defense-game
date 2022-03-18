package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel {
    private Dimension size;
    private Game game;

    public GameScreen(Game game) {
        this.game = game;

        setPanelSize();
    }

    private void setPanelSize() {
        size = new Dimension(640, 640);

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        game.getRender().render(graphics);
    }
}
