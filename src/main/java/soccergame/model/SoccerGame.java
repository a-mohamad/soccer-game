package soccergame.model;

import soccergame.model.players.GamePlayer;
import soccergame.model.players.Goalkeeper;
import soccergame.model.players.PlayerCollection;
import soccergame.model.players.PlayerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class SoccerGame {

    private final PlayerCollection gamePlayers;
    private TimerTask timerTask;
    private Integer timeRemaining;
    private Integer goal;
    private Boolean isPaused;
    private Boolean isOver;

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

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public Integer getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Integer timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer newGoal) {
        goal = newGoal;
    }

    public Boolean isPaused() {
        return isPaused;
    }

    public void setPaused(Boolean paused) {
        isPaused = paused;
    }

    public Boolean isOver() {
        return isOver;
    }

    public void setOver(Boolean over) {
        isOver = over;
    }

    public PlayerCollection getGamePlayers() {
        return gamePlayers;
    }

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

    public boolean isScored() {
        return SoccerBall.getSoccerBall().inGate();
    }

    public GamePlayer getActivePlayer() {
        return gamePlayers.get("Striker");
    }
}
