package inputs;

import main.Game;
import main.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {
    private Game game;

    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(GameStates.gameState == GameStates.MENU) {
                game.getMenu().mouseClicked(e.getX(), e.getY());
            } else if(GameStates.gameState == GameStates.PLAYING) {
                game.getPlaying().mouseClicked(e.getX(), e.getY());
            } else if(GameStates.gameState == GameStates.SETTINGS) {
                game.getSettings().mouseClicked(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(GameStates.gameState == GameStates.MENU) {
            game.getMenu().mousePressed(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().mousePressed(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.SETTINGS) {
            game.getSettings().mousePressed(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(GameStates.gameState == GameStates.MENU) {
                game.getMenu().mouseReleased(e.getX(), e.getY());
            } else if(GameStates.gameState == GameStates.PLAYING) {
                game.getPlaying().mouseReleased(e.getX(), e.getY());
            } else if(GameStates.gameState == GameStates.SETTINGS) {
                game.getSettings().mouseReleased(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(GameStates.gameState == GameStates.MENU) {
            game.getMenu().mouseDragged(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().mouseDragged(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.SETTINGS) {
            game.getSettings().mouseDragged(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(GameStates.gameState == GameStates.MENU) {
            game.getMenu().mouseMoved(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().mouseMoved(e.getX(), e.getY());
        } else if(GameStates.gameState == GameStates.SETTINGS) {
            game.getSettings().mouseMoved(e.getX(), e.getY());
        }
    }
}
