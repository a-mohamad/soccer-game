package soccergame.model.players;

import soccergame.model.SoccerBall;

import java.awt.*;
import java.util.Random;

/**
 * A representation of a goalkeeper in soccer.
 */
public class Goalkeeper extends GamePlayer {
    /**
     * The amount this {@code GoalKeeper} can move in one key press.
     */
    private int movementStep;

    /**
     * Construct a {@code GoalKeeper} with a specified name and color.
     *
     * @param name  the name of this {@code GoalKeeper}
     * @param color the color of this {@code GoalKeeper}
     */
    public Goalkeeper(String name, Color color) {
        super(name, color);
        movementStep = 10;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveLeft() {
        if (getPlayerPosition().x - 5 - movementStep > 0) {
            setPlayerPosition(new Point(getPlayerPosition().x - movementStep, getPlayerPosition().y));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveRight() {
        if (getPlayerPosition().x + 50 + movementStep < 600) {
            setPlayerPosition(new Point(getPlayerPosition().x + movementStep, getPlayerPosition().y));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveUp() {
        if (getPlayerPosition().y - 5 > 0) {
            setPlayerPosition(new Point(getPlayerPosition().x, getPlayerPosition().y - 5));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveDown() {
        if (getPlayerPosition().y + 50 < 300) {
            setPlayerPosition(new Point(getPlayerPosition().x, getPlayerPosition().y + 5));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shootBall() {
        SoccerBall.getSoccerBall().moveBall(-20, -5.0, -0.05);
    }

    /**
     * Move this {@code GoalKeeper} randomly on the x-axis with a tendency to
     * move in an opposite direction.
     */
    public void moveRandomly() {
        Random random = new Random();
        double playerCurrentXPosition = (double) getPlayerPosition().x + 15;
        double chanceOfMovingLeft = (playerCurrentXPosition - 300) / 100 - (random.nextGaussian());
        movementStep = (int) Math.abs(30 * chanceOfMovingLeft);
        if (chanceOfMovingLeft > 0) {
            moveLeft();
        } else {
            moveRight();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInitialPosition() {
        setPlayerPosition(new Point(280, 70));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return playerName + " caught " + playerStatistics.toString() + " balls";
    }
}
