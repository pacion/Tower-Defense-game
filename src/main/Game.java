package main;

import javax.swing.JFrame;

public class Game extends JFrame {
    private int width = 400;
    private int height = 400;
    private GameScreen gameScreen;

    public Game() {
        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen();
        add(gameScreen);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
