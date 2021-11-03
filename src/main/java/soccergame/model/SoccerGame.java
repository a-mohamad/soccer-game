package soccergame.model;

import soccergame.model.players.GamePlayer;
import soccergame.model.players.Goalkeeper;
import soccergame.model.players.PlayerCollection;
import soccergame.model.players.PlayerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A representation of a soccer game with a game loop.
 */
public class SoccerGame {
    /**
     * The list of players in this game.
     */
    private final PlayerCollection gamePlayers;
    /**
     * The timer task of the game loop.
     */
    private TimerTask timerTask;
    /**
     * The time remaining in this game.
     */
    private Integer timeRemaining;
    /**
     * The number of goals in this game.
     */
    private Integer goal;
    /**
     * The paused state of this game.
     */
    private Boolean isPaused;
    /**
     * The game state (i.e. playing, over).
     */
    private Boolean isOver;

    /**
     * Construct a new {@code SoccerGame} with default values.
     */
    public SoccerGame() {
        timeRemaining = 60;
        goal = 0;
        isPaused = false;
        isOver = false;
        SoccerBall.getSoccerBall().resetSoccerBall();
        PlayerFactory playerFactory = new PlayerFactory();
        gamePlayers = new PlayerCollection();
        gamePlayers.add(playerFactory.getPlayer("striker"));
        gamePlayers.add(playerFactory.getPlayer("goalkeeper"));
        startGame();
    }

    /**
     * Start the game loop and keep track of the game players actions.
     */
    private void startGame() {
        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!isPaused()) {
                    if (getTimeRemaining() <= 0) {
                        setOver(true);
                        timer.cancel();
                    } else {
                        setTimeRemaining(getTimeRemaining() - 1);
                    }
                    if (isScored()) {
                        setPaused(true);
                        setGoal(getGoal() + 1);
                        getActivePlayer().setPlayerStatistics(getActivePlayer().getPlayerStatistics() + 1);
                        getGamePlayers().get("Striker").setInitialPosition();
                        SoccerBall.getSoccerBall().resetSoccerBall();
                    } else {
                        automateGoalkeeper();
                    }
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    /**
     * Get a reference to the game loop timer for testing.
     *
     * @return the timer task for this games game loop
     */
    public TimerTask getTimerTask() {
        return timerTask;
    }

    /**
     * Get the amount of time remaining for this game.
     *
     * @return the amount of time remaining in this game
     */
    public Integer getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Set the amount of time remaining in this game.
     *
     * @param timeRemaining the new amount of time remaining
     */
    public void setTimeRemaining(Integer timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    /**
     * Get the number of goals in this game.
     *
     * @return the number of goals
     */
    public Integer getGoal() {
        return goal;
    }

    /**
     * Set the number of goals in this game.
     *
     * @param newGoal the new amount of goals
     */
    public void setGoal(Integer newGoal) {
        goal = newGoal;
    }

    /**
     * Check if the state of the game is paused.
     *
     * @return {@code true} if this game is paused
     */
    public Boolean isPaused() {
        return isPaused;
    }

    /**
     * Set the pause state of this game.
     *
     * @param paused the new paused state of this game
     */
    public void setPaused(Boolean paused) {
        isPaused = paused;
    }

    /**
     * Check if the state of this game is over.
     *
     * @return {@code true} if this game is over
     */
    public Boolean isOver() {
        return isOver;
    }

    /**
     * Set the over state of this game.
     *
     * @param over the new over state of this game
     */
    public void setOver(Boolean over) {
        isOver = over;
    }

    /**
     * Get the list of players in this game.
     *
     * @return the list of players
     */
    public PlayerCollection getGamePlayers() {
        return gamePlayers;
    }

    /**
     * Automate the goalkeeper's movements to randomly move and handle
     * its interaction with the soccer ball.
     */
    public void automateGoalkeeper() {
        SoccerBall soccerBall = SoccerBall.getSoccerBall();
        Goalkeeper goalkeeper = (Goalkeeper) gamePlayers.get("Goalkeeper");
        if (soccerBall.onGoalkeeperSide()) {
            goalkeeper.grabsBall();
            goalkeeper.shootBall();
            goalkeeper.setPlayerStatistics(goalkeeper.getPlayerStatistics() + 1);
        } else {
            goalkeeper.moveRandomly();
        }
    }

    /**
     * Check if the soccer ball has been scored.
     *
     * @return {@code true} if the soccer ball has been scored
     */
    public boolean isScored() {
        return SoccerBall.getSoccerBall().inGate();
    }

    /**
     * The active game player in the game (striker).
     *
     * @return the active player in this game
     */
    public GamePlayer getActivePlayer() {
        return gamePlayers.get("Striker");
    }
}
