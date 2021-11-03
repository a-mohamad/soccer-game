package soccergame.view;

import soccergame.model.SoccerBall;
import soccergame.model.SoccerGame;
import soccergame.model.players.GamePlayer;
import soccergame.model.players.PlayerCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The game panel where the game contents will be painted.
 */
public class GamePanel extends JPanel {
    /**
     * The font for the UI
     */
    private final Font uiFont;

    /**
     * An instance of a soccer game.
     */
    private SoccerGame game;

    /**
     * Construct a {@code GamePanel} and set up the game panel and the soccer game.
     */
    public GamePanel() {
        super(null);
        super.setBackground(new Color(112, 176, 49));
        uiFont = new Font("UI", Font.BOLD, 15);
        setupSoccerGame();
        setupRepaint();
    }

    /**
     * Set up a new soccer game.
     */
    public void setupSoccerGame() {
        game = new SoccerGame();
    }

    /**
     * Set up the repaint task to manage repainting the
     * game panel.
     */
    private void setupRepaint() {
        java.util.Timer timer = new Timer();
        TimerTask repaintTask = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };
        timer.schedule(repaintTask, 0, 10);
    }

    /**
     * Get the instance of this game panel's soccer game.
     *
     * @return the soccer game
     */
    public SoccerGame getGame() {
        return game;
    }

    /**
     * Paint all the required components of the game panel.
     *
     * @param g the graphics ctx of this game panel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPausedText(g);
        paintGate(g);
        paintPenaltyLine(g);
        paintGoal(g);
        paintTimer(g);
        paintPlayers(g);
        paintBall(g);
        paintStatistics(g);
    }

    /**
     * Paint the paused state text.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintPausedText(Graphics g) {
        if (getGame().isPaused()) {
            g.setColor(Color.red);
            g.setFont(uiFont);
            g.drawString("Paused", 270, 300);
        }
    }

    /**
     * Paint the soccer net.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintGate(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(200, 10, 200, 50);
        g.setColor(Color.black);
        g.setFont(uiFont);
        g.drawString("Gate", 280, 40);
    }

    /**
     * Paint the penalty separation line.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintPenaltyLine(Graphics g) {
        g.setColor(Color.darkGray);
        g.drawLine(0, 200, 600, 200);
    }

    /**
     * Paint the game timer in the top left corner.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintTimer(Graphics g) {
        g.setColor(Color.black);
        g.setFont(uiFont);
        g.drawString("Time: " + getGame().getTimeRemaining(), 20, 25);
    }

    /**
     * Paint the goal counter in the top left corner.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintGoal(Graphics g) {
        g.setColor(Color.black);
        g.setFont(uiFont);
        g.drawString("Goal: " + game.getGoal(), 20, 50);
    }

    /**
     * Paint the players in the game in their initial positions.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintPlayers(Graphics g) {
        PlayerCollection gamePlayers = game.getGamePlayers();
        for (GamePlayer player : gamePlayers) {
            g.setColor(player.getPlayerColor());
            g.drawOval(player.getPlayerPosition().x, player.getPlayerPosition().y, 30, 30);
            g.fillPolygon(new int[]{
                    player.getPlayerPosition().x, player.getPlayerPosition().x + 15, player.getPlayerPosition().x + 30
            }, new int[]{
                    player.getPlayerPosition().y + 30, player.getPlayerPosition().y + 70, player.getPlayerPosition().y + 30
            }, 3);
            g.setColor(Color.black);
            g.setFont(uiFont);
            g.drawString(player.getPlayerName(), player.getPlayerPosition().x - (int) (1.8 * player.getPlayerName().length()), player.getPlayerPosition().y + 85);
        }
    }

    /**
     * Paint the soccer ball in its default position.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintBall(Graphics g) {
        SoccerBall soccerBall = SoccerBall.getSoccerBall();
        g.setColor(soccerBall.getColor());
        g.fillOval(soccerBall.getPosition().x, soccerBall.getPosition().y, 20, 20);
    }

    /**
     * Paint the statistics in the game over state.
     *
     * @param g the graphics ctx of this game panel
     */
    private void paintStatistics(Graphics g) {
        if (getGame().isOver()) {
            g.setColor(Color.red);
            g.setFont(uiFont);
            g.drawString("Game Over!", 250, 250);
            PlayerCollection gamePlayers = game.getGamePlayers();
            gamePlayers.sort();
            int y = 300;
            for (GamePlayer player : gamePlayers) {
                g.drawString(player.toString(), 200, y);
                y = y + 30;
            }
        }
    }
}
