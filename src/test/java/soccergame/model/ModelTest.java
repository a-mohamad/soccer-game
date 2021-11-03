package soccergame.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import soccergame.model.players.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Collection of tests for the soccergame's model code
 */
class ModelTest {
    static final Random random = new Random();
    final PlayerFactory pf = new PlayerFactory();
    final Striker striker = (Striker) pf.getPlayer("striker");
    final Point strikerPos = striker.getPlayerPosition();
    final Goalkeeper goalkeeper = (Goalkeeper) pf.getPlayer("goalkeeper");
    final Point goalkeeperPos = goalkeeper.getPlayerPosition();
    final int goalkeeperMoveStep = 10;
    final int strikerMoveStep = 5;

    // collection of players to test PlayerCollection
    static Stream<Arguments> listOfPlayers() {
        PlayerCollection players = new PlayerCollection();
        players.addAll(List.of(new Striker("player 1", Color.BLUE),
                new Striker("player 2", Color.YELLOW),
                new Striker("player 3", Color.RED),
                new Goalkeeper("player 4", Color.PINK),
                new Goalkeeper("player 5", Color.GREEN),
                new Goalkeeper("player 6", Color.BLUE)));

        for (GamePlayer player : players) {
            player.setPlayerStatistics(random.nextInt());
            if (player.getClass() == Goalkeeper.class)
                player.setPlayerPosition(new Point(600, 0));
        }

        return Stream.of(Arguments.of(players));
    }

    @Test
    void playerFactoryTest() {
        GamePlayer striker = pf.getPlayer("Striker");
        assertEquals(striker.getClass(), Striker.class);

        GamePlayer goalkeeper = pf.getPlayer("Goalkeeper");
        assertEquals(goalkeeper.getClass(), Goalkeeper.class);

        assertThrows(IllegalArgumentException.class, () -> pf.getPlayer("invalid"));
    }

    @Test
    void goalkeeperTest() {
        assertFalse(goalkeeper.isPlayerHasBall());
        goalkeeper.grabsBall();
        assertTrue(goalkeeper.isPlayerHasBall());
        goalkeeper.moveRandomly();
        goalkeeper.shootBall();
        assertEquals("goalkeeper", goalkeeper.getPlayerName().toLowerCase());
        assertEquals(Color.YELLOW, goalkeeper.getPlayerColor());
        resetPlayerPositions();
        assertEquals(new Point(280, 70), goalkeeper.getPlayerPosition());
        goalkeeper.setPlayerPosition(new Point(30, 30));
        assertEquals(0, goalkeeper.compareTo(striker));
    }

    @Test
    void strikerTest() {
        striker.grabsBall();
        assertTrue(striker.isPlayerHasBall());
        SoccerBall.getSoccerBall().setVelocity(8.0);
        striker.shootBall();
        // bypass timer task for ball
        SoccerBall.getSoccerBall().setPosition(new Point());
        assertFalse(striker.isPlayerHasBall());
        assertEquals("striker", striker.getPlayerName().toLowerCase());
        assertEquals(Color.BLUE, striker.getPlayerColor());
        resetPlayerPositions();
        assertEquals(new Point(500, 450), striker.getPlayerPosition());
        striker.setPlayerPosition(new Point(30, 30));
        assertEquals(0, striker.compareTo(goalkeeper));
        SoccerBall.getSoccerBall().resetSoccerBall();
    }

    @Test
    void soccerBallTest() {
        SoccerBall.getSoccerBall().resetSoccerBall();
        assertFalse(SoccerBall.getSoccerBall().inGate());
        // 180 >  400 <  10 > 60 <
        SoccerBall.getSoccerBall().setPosition(new Point(200, 40));
        SoccerBall.getSoccerBall().moveBallY(10);
        assertTrue(SoccerBall.getSoccerBall().inGate());

        // edge cases return false
        SoccerBall.getSoccerBall().resetSoccerBall();
        SoccerBall.getSoccerBall().setPosition(new Point(200, -1000));
        assertEquals(new Point(200,-1000), SoccerBall.getSoccerBall().getPosition());
        assertFalse(SoccerBall.getSoccerBall().inGate());
        SoccerBall.getSoccerBall().resetSoccerBall();
    }

    @Test
    void moveLeftTest() {
        goalkeeper.moveLeft();
        striker.moveLeft();
        goalkeeperPos.translate(-goalkeeperMoveStep, 0);
        strikerPos.translate(-strikerMoveStep, 0);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        // edge case, do nothing
        goalkeeper.setPlayerPosition(new Point(-1000, -1000));
        striker.setPlayerPosition(new Point(-1000, -1000));
        goalkeeper.moveLeft();
        striker.moveLeft();
        assertEquals(new Point(-1000, -1000), goalkeeper.getPlayerPosition());
        assertEquals(new Point(-1000, -1000), striker.getPlayerPosition());
        resetPlayerPositions();
    }

    @Test
    void moveRightTest() {
        goalkeeper.moveRight();
        striker.moveRight();
        goalkeeperPos.translate(+goalkeeperMoveStep, 0);
        strikerPos.translate(+strikerMoveStep, 0);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        // edge case, do nothing
        goalkeeper.setPlayerPosition(new Point(1000, 1000));
        striker.setPlayerPosition(new Point(1000, 1000));
        goalkeeper.moveRight();
        striker.moveRight();
        assertEquals(new Point(1000, 1000), goalkeeper.getPlayerPosition());
        assertEquals(new Point(1000, 1000), striker.getPlayerPosition());
        resetPlayerPositions();
    }

    @Test
    void moveUpTest() {
        goalkeeper.moveUp();
        striker.moveUp();
        goalkeeperPos.translate(0, -strikerMoveStep);
        strikerPos.translate(0, -strikerMoveStep);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        // edge case, do nothing
        goalkeeper.setPlayerPosition(new Point(-1000, -1000));
        striker.setPlayerPosition(new Point(-1000, -1000));
        goalkeeper.moveUp();
        striker.moveUp();
        assertEquals(new Point(-1000, -1000), goalkeeper.getPlayerPosition());
        assertEquals(new Point(-1000, -1000), striker.getPlayerPosition());
        resetPlayerPositions();
    }

    @Test
    void moveDownTest() {
        goalkeeper.moveDown();
        striker.moveDown();
        goalkeeperPos.translate(0, +strikerMoveStep);
        strikerPos.translate(0, +strikerMoveStep);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPlayerPositions();
        striker.setPlayerPosition(new Point(0, 350));
        strikerPos.setLocation(new Point(0, 350));
        striker.moveDown();
        strikerPos.translate(0, +strikerMoveStep);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        // edge case, do nothing
        goalkeeper.setPlayerPosition(new Point(1000, 1000));
        striker.setPlayerPosition(new Point(1000, 1000));
        goalkeeper.moveDown();
        striker.moveDown();
        assertEquals(new Point(1000, 1000), goalkeeper.getPlayerPosition());
        assertEquals(new Point(1000, 1000), striker.getPlayerPosition());
        resetPlayerPositions();
    }

    @Test
    public void moveRandomlyTest() {
        // player is more to the right side
        goalkeeper.setPlayerPosition(new Point(0, 0));
        goalkeeper.moveRandomly();
        // player is more to the left side
        goalkeeper.setPlayerPosition(new Point(600, 0));
        goalkeeper.moveRandomly();
        resetPlayerPositions();
    }

    void resetPlayerPositions() {
        goalkeeper.setInitialPosition();
        striker.setInitialPosition();
        goalkeeperPos.setLocation(goalkeeper.getPlayerPosition());
        strikerPos.setLocation(striker.getPlayerPosition());
    }

    @Test
    void playerStatisticsTest() {
        goalkeeper.setPlayerStatistics(5);
        striker.setPlayerStatistics(5);
        assertEquals(5, striker.getPlayerStatistics());
        assertEquals(5, striker.getPlayerStatistics());
        assertEquals("goalkeeper caught 5 balls", goalkeeper.toString());
        assertEquals("striker scored 5 goals", striker.toString());
    }

    @Test
    void soccerGameTest() {
        // start the simulation of a simple game
        SoccerGame game = new SoccerGame();
        game.setPaused(false);
        game.setOver(false);
        assertEquals("striker", game.getGamePlayers().get(0).getPlayerName());
        assertEquals("goalkeeper", game.getGamePlayers().get(1).getPlayerName());
        assertEquals("striker", game.getActivePlayer().getPlayerName());
        assertEquals(Color.white, SoccerBall.getSoccerBall().getColor());

        int i;

        // kick ball past half point
        for (i = 0; i < 1; i++) {
            SoccerBall.getSoccerBall().setPosition(new Point(0, 100));
            game.getTimerTask().run();
            assertTrue(SoccerBall.getSoccerBall().onGoalkeeperSide());
        }
        // pause and unpause for a cycle
        game.setPaused(true);
        game.getTimerTask().run();
        game.setPaused(false);

        // continue countdown
        for (; i < 59; i++)
            game.getTimerTask().run();

        // edge case
        SoccerBall.getSoccerBall().setVelocity(1);

        // score ball in last cycle
        for (; i < 60; i++) {
            SoccerBall.getSoccerBall().setPosition(new Point(200, 40));
            game.getTimerTask().run();
        }

        // unpause and end game
        game.setPaused(false);
        game.getTimerTask().run();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void sortPlayersTest(PlayerCollection players) {
        players.sort();
        for (int i = 1; i < players.size(); i++)
            assertTrue(players.get(i - 1).compareTo(players.get(i)) < 0);
        PlayerCollection empty = new PlayerCollection();
        empty.sort();
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void getByNameTest(PlayerCollection players) {
        assertEquals(players.get("player 1").getPlayerColor(), Color.BLUE);
        assertEquals(players.get("player 2").getPlayerColor(), Color.YELLOW);
        assertEquals(players.get("player 3").getPlayerColor(), Color.RED);
        assertEquals(players.get("player 4").getPlayerColor(), Color.PINK);
        assertEquals(players.get("player 5").getPlayerColor(), Color.GREEN);
        assertEquals(players.get("player 6").getPlayerColor(), Color.BLUE);
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void getByIndexTest(PlayerCollection players) {
        assertEquals(players.get(0).getPlayerColor(), Color.BLUE);
        assertEquals(players.get(1).getPlayerColor(), Color.YELLOW);
        assertEquals(players.get(2).getPlayerColor(), Color.RED);
        assertEquals(players.get(3).getPlayerColor(), Color.PINK);
        assertEquals(players.get(4).getPlayerColor(), Color.GREEN);
        assertEquals(players.get(5).getPlayerColor(), Color.BLUE);
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void noSuchElementTest(PlayerCollection players) {
        assertNull(players.get(-1));
        assertNull(players.get("player -1"));
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    void playerIteratorTest(PlayerCollection players) {
        Iterator<GamePlayer> iterator = players.iterator();
        int idx = 1;
        while (iterator.hasNext()) {
            GamePlayer next = iterator.next();
            assertEquals(String.format("player %d", idx++), next.getPlayerName());
        }
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
