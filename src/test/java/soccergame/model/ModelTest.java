package soccergame.model;

import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import soccergame.model.players.*;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

/**
 * Collection of tests for the soccergame's model code
 */
public class ModelTest {
    final PlayerFactory pf = new PlayerFactory();
    final Striker striker = (Striker) pf.getPlayer("striker");
    final Goalkeeper goalkeeper = (Goalkeeper) pf.getPlayer("goalkeeper");

    /**
     * A test should have clear documentation on what the test achieves, its preconditions and any
     * other relevant details.
     * <p>
     * ie. This is a sample test template.
     */
    @Test
    void sampleTest() {
        assertFalse(false);
        assertTrue(true);
        assertEquals("", "");
    }

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
        goalkeeper.moveLeft();
        goalkeeper.moveRight();
        goalkeeper.moveUp();
        goalkeeper.moveDown();
        goalkeeper.shootBall();
        goalkeeper.getPlayerName();
        goalkeeper.getPlayerColor();
        goalkeeper.getPlayerPosition();
        goalkeeper.setInitialPosition();
        goalkeeper.setPlayerPosition(new Point(30, 30));
        goalkeeper.getPlayerStatistics();
        goalkeeper.setPlayerStatistics(10);
        goalkeeper.compareTo(goalkeeper);
        goalkeeper.toString();
    }

    static Stream<Arguments> listOfPlayers() {
        PlayerCollection players = new PlayerCollection();
        players.addAll(List.of(new Striker("player 1", Color.BLUE),
                new Striker("player 2", Color.BLUE),
                new Striker("player 3", Color.BLUE),
                new Goalkeeper("player 4", Color.BLUE),
                new Goalkeeper("player 5", Color.BLUE),
                new Goalkeeper("player 6", Color.BLUE)));

        return Stream.of(Arguments.of(players));
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
        striker.getPlayerName();
        striker.getPlayerColor();
        striker.getPlayerPosition();
        striker.setInitialPosition();
        striker.setPlayerPosition(new Point(30, 30));
        striker.getPlayerStatistics();
        striker.setPlayerStatistics(10);
        striker.compareTo(goalkeeper);
        striker.toString();
    }
    
    @Test
    void PlayerStatistics() {
    	striker.setPlayerStatistics(5);
    	int score = striker.getPlayerStatistics();
    	assertTrue(5 == score);
    	assertEquals("striker scored 5 goals", striker.toString());
    }

    @ParameterizedTest
    @MethodSource("listOfPlayers")
    void playerIterator(PlayerCollection players) {
        for (GamePlayer player: players)
            assertEquals(player.getPlayerColor(), Color.BLUE);
    }


}
