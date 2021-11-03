package soccergame.controller;

import soccergame.model.SoccerGame;
import soccergame.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A game listener for the {@code Striker}. Implements {@code KeyListener}
 * to control the player movement.
 */
public class GameListener implements KeyListener {
    /**
     * The {@code GamePanel} to apply the key listener to.
     */
    private final GamePanel gamePanel;

    /**
     * Construct a {@code GameListener} with a specified panel.
     *
     * @param panel the game panel to listen for key events
     */
    public GameListener(GamePanel panel) {
        gamePanel = panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        SoccerGame soccerGame = gamePanel.getGame();
        if (!soccerGame.isPaused() && !soccerGame.isOver()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    soccerGame.getActivePlayer().moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    soccerGame.getActivePlayer().moveRight();
                    break;
                case KeyEvent.VK_UP:
                    soccerGame.getActivePlayer().moveUp();
                    break;
                case KeyEvent.VK_DOWN:
                    soccerGame.getActivePlayer().moveDown();
                    break;
                case KeyEvent.VK_SPACE:
                    if (soccerGame.getActivePlayer().isPlayerHasBall()) {
                        soccerGame.getActivePlayer().shootBall();
                    }
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
