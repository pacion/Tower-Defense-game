package main;

import javax.swing.JPanel;
import java.awt.*;

public class GameScreen extends JPanel {

    public GameScreen() {

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(new Color(90, 8, 24));
        graphics.fillRect(50, 50, 100, 100);
    }
}
