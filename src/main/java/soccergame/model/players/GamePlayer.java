package soccergame.model.players;

import soccergame.model.SoccerBall;

import java.awt.*;

/**
 * An abstract game player which implements {@code Comparable} to help
 * sort players by their {@code PlayerStatistics} attributes.
 */
public abstract class GamePlayer implements Comparable<GamePlayer> {
    /**
     * This player's name.
     */
    protected final String playerName;

    /**
     * This player's rendered color.
     */
    protected final Color playerColor;
    /**
     * This player's statistics attributes.
     */
    protected final PlayerStatistics playerStatistics;
    /**
     * This player's position within the {@code GamePanel}.
     */
    protected Point playerPosition;

    /**
     * Construct a {@code GamePlayer} with a specified name and
     * color. Additionally, it is initialized with default positions
     * and statistics.
     *
     * @param name  this player's name
     * @param color this player's color
     */
    public GamePlayer(String name, Color color) {
        playerName = name;
        playerColor = color;
        playerStatistics = new PlayerStatistics();
        setInitialPosition();
    }

    /**
     * Check if this player has the ball in their possession.
     *
     * @return {@code true} if this player has the ball
     */
    public boolean isPlayerHasBall() {
        Point playerPositionCenter = new Point(getPlayerPosition().x + 15, getPlayerPosition().y + 30);
        return playerPositionCenter.distance(SoccerBall.getSoccerBall().getPosition()) < 55;
    }

    /**
     * Make this player grab the soccer ball.
     */
    public void grabsBall() {
        SoccerBall ball = SoccerBall.getSoccerBall();
        if (getPlayerPosition().x + 15 > ball.getPosition().x) {
            ball.setPosition(new Point(getPlayerPosition().x - 10, getPlayerPosition().y + 55));
        } else {
            ball.setPosition(new Point(getPlayerPosition().x + 20, getPlayerPosition().y + 55));
        }
    }

    /**
     * Moves this player to the left within the {@code GamePanel}.
     */
    public abstract void moveLeft();

    /**
     * Moves this player to the right within the {@code GamePanel}.
     */
    public abstract void moveRight();

    /**
     * Moves this player to the up within the {@code GamePanel}.
     */
    public abstract void moveUp();

    /**
     * Moves this player to the down within the {@code GamePanel}.
     */
    public abstract void moveDown();

    /**
     * Simulates this player kicking the soccer ball.
     */
    public abstract void shootBall();

    /**
     * Get the name of this player.
     *
     * @return the name of this player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get the color of this player.
     *
     * @return the color of this player
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * Get the position of this player.
     *
     * @return the position of this player
     */
    public Point getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Set a new position within the {@code GamePanel} for this
     * player.
     *
     * @param newPosition the new position for this player
     */
    public void setPlayerPosition(Point newPosition) {
        playerPosition = newPosition;
        if (isPlayerHasBall()) {
            grabsBall();
        }
    }

    /**
     * Sets the initial starting point of this player within
     * the {@code GamePanel}.
     */
    public abstract void setInitialPosition();

    /**
     * Get the game statistics for this player.
     *
     * @return the statistics attribute of {@code PlayerStatistics}
     */
    public Integer getPlayerStatistics() {
        return playerStatistics.getStatistics();
    }

    /**
     * Set the game statistics for this plaeyr.
     *
     * @param newStatistics the new attribute to set for this player's {@code PlayerStatistics}
     */
    public void setPlayerStatistics(Integer newStatistics) {
        playerStatistics.setStatistics(newStatistics);
    }

    /**
     * Compares this player to another by their {@code PlayerStatistics} attribute.
     *
     * @param otherPlayer the other {@code GamePlayer} to compare to
     * @return the value {@code 0} if they are equal;
     * a negative integer if the first {@code GamePlayer}
     * has lower statistics than the second; or a
     * positive integer if the first {@code GamePlayer}
     * has higher statistics than the second
     */
    @Override
    public int compareTo(GamePlayer otherPlayer) {
        return otherPlayer.getPlayerStatistics().compareTo(this.getPlayerStatistics());
    }

    /**
     * Returns the statistics of this player in a readable format.
     *
     * @return a summary of this player's statistics
     */
    @Override
    public abstract String toString();
}
