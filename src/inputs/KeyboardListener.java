package inputs;

import main.GameStates;
import scenes.Playing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A)
            GameStates.gameState = GameStates.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
