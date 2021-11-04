package soccergame.model;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A representation of a soccer ball which maintains a static instance of
 * a single game ball.
 */
public class SoccerBall {
    /**
     * A static soccer ball to maintain.
     */
    private static final SoccerBall soccerBall = new SoccerBall();
    /**
     * The color of this soccer ball.
     */
    private final Color color;
    /**
     * The position of this soccer ball.
     */
    private Point position;
    /**
     * The speed at which this soccer ball moves at.
     */
    private double velocity;

    /**
     * Set the default position and color of this soccer ball instance.
     */
    private SoccerBall() {
        color = Color.white;
        resetSoccerBall();
    }

    /**
     * Get a reference to the static soccer ball.
     *
     * @return the instance of this soccer ball
     */
    public static SoccerBall getSoccerBall() {
        return soccerBall;
    }

    /**
     * Move this soccer ball with a specified initial distance, velocity,
     * and acceleration of the movement.
     *
     * @param initialDistance the initial distance to move this soccer ball
     * @param initialVelocity the initial velocity of the movement when repainting
     * @param acceleration    the acceleration factor of this soccer ball
     */
    public void moveBall(int initialDistance, double initialVelocity, double acceleration) {
        moveBallY(initialDistance);
        setVelocity(initialVelocity);
        Timer timer = new Timer();
        TimerTask repaintTask = new TimerTask() {
            @Override
            public void run() {
                if (Math.abs(velocity) < 2) {
                    velocity = 0.0;
                    timer.cancel();
                } else {
                    velocity = velocity - acceleration;
                }
                moveBallY((int) velocity);
            }
        };
        timer.schedule(repaintTask, 0, 10);
    }

    /**
     * Move this soccer ball in the y-axis with a specified distance.
     *
     * @param distance the distance to move this soccer ball
     */
    public void moveBallY(int distance) {
        if (getPosition().y + distance < 510 && getPosition().y - distance > 20) {
            setPosition(new Point(getPosition().x, getPosition().y - distance));
        } else {
            setVelocity(0.0);
        }
    }

    /**
     * Reset the velocity and position of this soccer ball.
     */
    public void resetSoccerBall() {
        setVelocity(0.0);
        setPosition(new Point(480, 500));
    }

    /**
     * Check if this soccer ball is on the goalkeeper's side.
     *
     * @return {@code true} if this soccer ball is on the goalkeeper's side
     */
    public boolean onGoalkeeperSide() {
        return getPosition().y < 200;
    }

    /**
     * Check if this soccer ball is in the net.
     *
     * @return {@code true} if this soccer ball is in the net
     */
    public boolean inGate() {
        return getPosition().x > 180 && getPosition().x < 400 && getPosition().y > 10 && getPosition().y < 60;
    }

    /**
     * Check if the ball is in a dead state after being shot
     *
     * @return {@code true} if this soccer ball was failed to be scored
     */
    public boolean isDead() {
       return soccerBall.getVelocity() == 0 && soccerBall.onGoalkeeperSide() && !inGate();
    }

    /**
     * Get the velocity of this soccer ball.
     *
     * @return the velocity of this soccer ball
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Set the velocity of this soccer ball's movement.
     *
     * @param velocity the velocity of this soccer ball
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Get the current position of this soccer ball within the {@code GamePanel}.
     *
     * @return the position of this soccer ball
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set the position of this soccer ball within the {@code GamePanel}.
     *
     * @param position the new position for this soccer ball
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Get the color of this soccer ball.
     *
     * @return the color of this soccer ball
     */
    public Color getColor() {
        return color;
    }
}
