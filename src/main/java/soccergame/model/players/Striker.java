package soccergame.model.players;

import soccergame.model.SoccerBall;

import java.awt.*;

/**
 * A representation of a striker in soccer.
 */
public class Striker extends GamePlayer {

    /**
     * Construct a {@code Striker} with a specified name and color.
     *
     * @param name  the name of this {@code Striker}
     * @param color the color of this {@code Striker}
     */
    public Striker(String name, Color color) {
        super(name, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveLeft() {
        if (getPlayerPosition().x - 10 > 0) {
            setPlayerPosition(new Point(getPlayerPosition().x - 5, getPlayerPosition().y));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveRight() {
        if (getPlayerPosition().x + 50 < 600) {
            setPlayerPosition(new Point(getPlayerPosition().x + 5, getPlayerPosition().y));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveUp() {
        if (getPlayerPosition().y - 5 > 200) {
            setPlayerPosition(new Point(getPlayerPosition().x, getPlayerPosition().y - 5));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveDown() {
        if (getPlayerPosition().y + 50 < 500) {
            setPlayerPosition(new Point(getPlayerPosition().x, getPlayerPosition().y + 5));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shootBall() {
        SoccerBall.getSoccerBall().moveBall(60, 5.0, 0.05);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInitialPosition() {
        setPlayerPosition(new Point(500, 450));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return playerName + " scored " + playerStatistics.toString() + " goals";
    }
}
