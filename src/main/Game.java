package main;

import handlers.TileHandler;
import helperMethods.LoadSave;
import scenes.*;

import javax.swing.*;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    // Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;

    private TileHandler tileHandler;

    public Game() {
        LoadSave.CreateFolder();
        createDefaultLevel();
        initClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // TODO: change into middle of the screen good size, resolution
        setResizable(false);
        setTitle("Tower defense");
        add(gameScreen);
        pack();
        setVisible(true);
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++)
            arr[i] = 0;

        LoadSave.CreateLevel(arr);
    }

    private void initClasses() {
        tileHandler = new TileHandler();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);
    }

    private void start() {
        gameThread = new Thread(this) {
        };

        gameThread.start();
    }

    private void updateGame() {
        if (GameStates.gameState == GameStates.EDIT) {
            editing.update();
        } else if (GameStates.gameState == GameStates.MENU) {

        } else if (GameStates.gameState == GameStates.PLAYING) {
            playing.update();
        } else if (GameStates.gameState == GameStates.SETTINGS) {

        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {
            now = System.nanoTime();

            // Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                // ---------------------- UNCOMMENT TO SEE FPS, UPS CHANGES ----------------------
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getters and setters
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }


    public Settings getSettings() {
        return settings;
    }

    public Editing getEditor() {
        return editing;
    }

    public TileHandler getTileHandler() {
        return tileHandler;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

}