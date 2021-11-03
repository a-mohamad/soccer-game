package soccergame.controller;

import soccergame.model.SoccerGame;
import soccergame.view.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A menu listener for the {@code GamePanel}. Implements {@code ActionListener}
 * to control the game flow.
 */
public class MenubarListener implements ActionListener {
    /**
     * The {@code GamePanel} to apply the action listener to.
     */
    private final GamePanel gamePanel;

    /**
     * Construct a {@code MenubarListener} with a specified panel.
     *
     * @param panel the game panel to listen for menu events
     */
    public MenubarListener(GamePanel panel) {
        gamePanel = panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SoccerGame soccerGame = gamePanel.getGame();
        switch (e.getActionCommand()) {
            case "NEW":
                gamePanel.setupSoccerGame();
                break;
            case "EXIT":
                System.exit(0);
                break;
            case "PAUSE":
                if (!soccerGame.isPaused() && !soccerGame.isOver()) {
                    soccerGame.setPaused(true);
                } else if (soccerGame.isPaused()) {
                    System.out.println("game is already on pause!");
                } else if (soccerGame.isOver()) {
                    System.out.println("game is over, please start a new game.");
                }
                break;
            case "RESUME":
                if (soccerGame.isPaused() && !soccerGame.isOver()) {
                    soccerGame.setPaused(false);
                } else if (!soccerGame.isPaused()) {
                    System.out.println("game is already running!");
                } else if (soccerGame.isOver()) {
                    System.out.println("game is over, please start a new game.");
                }
                break;
            default:
                throw new RuntimeException("Invalid action command " + e.getActionCommand());
        }
    }
}
