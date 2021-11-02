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
    final PlayerFactory pf = new PlayerFactory();
    final Striker striker = (Striker) pf.getPlayer("striker");
    final Goalkeeper goalkeeper = (Goalkeeper) pf.getPlayer("goalkeeper");
    final Point strikerPos = striker.getPlayerPosition();
    final Point goalkeeperPos = goalkeeper.getPlayerPosition();
    final int goalkeeperMoveStep = 10;
    final int strikerMoveStep = 5;
    static final Random random = new Random();


    @Test
    void playerFactoryTest() {
        GamePlayer striker = pf.getPlayer("Striker");
        assertEquals(striker.getClass(), Striker.class);

        GamePlayer goalkeeper = pf.getPlayer("Goalkeeper");
        assertEquals(goalkeeper.getClass(), Goalkeeper.class);

        assertThrows(IllegalArgumentException.class, () -> {
            pf.getPlayer("invalid");
        });
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
        resetPositions();
        assertEquals(new Point(280, 70), goalkeeper.getPlayerPosition());
        goalkeeper.setPlayerPosition(new Point(30, 30));
        assertEquals(0, goalkeeper.compareTo(striker));
    }

    @Test
    void strikerTest() throws InterruptedException {
        striker.grabsBall();
        assertTrue(striker.isPlayerHasBall());
        SoccerBall.getSoccerBall().setVelocity(8.0);
        striker.shootBall();
        // bypass timer task for ball
        SoccerBall.getSoccerBall().setPosition(new Point());
        assertFalse(striker.isPlayerHasBall());
        assertEquals("striker", striker.getPlayerName().toLowerCase());
        assertEquals(Color.BLUE, striker.getPlayerColor());
        resetPositions();
        assertEquals(new Point(500, 450), striker.getPlayerPosition());
        striker.setPlayerPosition(new Point(30, 30));
        assertEquals(0, striker.compareTo(goalkeeper));
    }

    @Test
    void moveLeftTest() {
        goalkeeper.moveLeft();
        striker.moveLeft();
        goalkeeperPos.translate(-goalkeeperMoveStep, 0);
        strikerPos.translate(-strikerMoveStep, 0);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPositions();
    }

    @Test
    void moveRightTest() {
        goalkeeper.moveRight();
        striker.moveRight();
        goalkeeperPos.translate(+goalkeeperMoveStep, 0);
        strikerPos.translate(+strikerMoveStep, 0);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPositions();
    }

    @Test
    void moveUpTest() {
        goalkeeper.moveUp();
        striker.moveUp();
        goalkeeperPos.translate(0, -strikerMoveStep);
        strikerPos.translate(0, -strikerMoveStep);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPositions();
    }

    @Test
    void moveDownTest() {
        goalkeeper.moveDown();
        striker.moveDown();
        goalkeeperPos.translate(0, +strikerMoveStep);
        strikerPos.translate(0, +strikerMoveStep);
        assertEquals(goalkeeper.getPlayerPosition(), goalkeeperPos);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPositions();
        striker.setPlayerPosition(new Point(0, 350));
        strikerPos.setLocation(new Point(0, 350));
        striker.moveDown();
        strikerPos.translate(0, +strikerMoveStep);
        assertEquals(striker.getPlayerPosition(), strikerPos);
        resetPositions();
    }

    void resetPositions() {
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
        }

        assertTrue(SoccerBall.getSoccerBall().onGoalkeeperSide());

        // continue countdown
        for (; i < 59; i++)
            game.getTimerTask().run();

        SoccerBall.getSoccerBall().setVelocity(1);

        // score ball
        for (; i < 60; i++) {
            SoccerBall.getSoccerBall().setPosition(new Point(200, 40));
            game.getTimerTask().run();
        }

        // unpause and end game
        game.setPaused(false);
        game.getTimerTask().run();
        assertTrue(game.isOver());
    }

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

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void sortPlayersTest(PlayerCollection players) {
        players.sort();
        for (int i = 1; i < players.size(); i++)
            assertTrue(players.get(i - 1).compareTo(players.get(i)) < 0);
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

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    public void moveRandomlyTest(PlayerCollection players) {
        for (GamePlayer player : players)
            if (player.getClass() == Goalkeeper.class) {
                player.setPlayerPosition(new Point(600, 600));
                ((Goalkeeper) player).moveRandomly();
            }
    }
}
