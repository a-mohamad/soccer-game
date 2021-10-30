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
        goalkeeper.isPlayerHasBall();
        goalkeeper.grabsBall();
        goalkeeper.moveRandomly();
        goalkeeper.moveLeft();
        goalkeeper.moveRight();
        goalkeeper.moveUp();
        goalkeeper.moveDown();
        goalkeeper.shootBall();
        assertEquals("goalkeeper", goalkeeper.getPlayerName().toLowerCase());
        assertEquals(Color.YELLOW, goalkeeper.getPlayerColor());
        goalkeeper.setInitialPosition();
        assertEquals(new Point(280, 70), goalkeeper.getPlayerPosition());
        goalkeeper.setPlayerPosition(new Point(30, 30));
        goalkeeper.getPlayerStatistics();
        assertEquals(0, goalkeeper.compareTo(striker));
        goalkeeper.setPlayerStatistics(10);
        assertEquals("goalkeeper caught 10 balls", goalkeeper.toString());
    }

    @Test
    void strikerTest() {
        striker.isPlayerHasBall();
        striker.grabsBall();
        striker.moveLeft();
        striker.moveRight();
        striker.moveUp();
        striker.moveDown();
        striker.shootBall();
        assertEquals("striker", striker.getPlayerName().toLowerCase());
        assertEquals(Color.BLUE, striker.getPlayerColor());
        striker.setInitialPosition();
        assertEquals(new Point(500, 450), striker.getPlayerPosition());
        striker.setPlayerPosition(new Point(30, 30));
        striker.getPlayerStatistics();
        assertEquals(0, striker.compareTo(goalkeeper));
        striker.setPlayerStatistics(10);
        assertEquals("striker scored 10 goals", striker.toString());
    }

    @Test
    void soccerGameTest() {
        // start the soccer game
        SoccerGame game = new SoccerGame();
        game.setPaused(false);
        game.setOver(false);
        assertEquals("striker", game.getGamePlayers().get(0).getPlayerName());
        assertEquals("goalkeeper", game.getGamePlayers().get(1).getPlayerName());
        game.getActivePlayer();
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

    @Test
    void playerStatisticsTest() {
        striker.setPlayerStatistics(5);
        int score = striker.getPlayerStatistics();
        assertEquals(5, score);
        assertEquals("striker scored 5 goals", striker.toString());
    }

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
