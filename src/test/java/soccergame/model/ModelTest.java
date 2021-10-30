package soccergame.model;

import org.junit.jupiter.api.Test;
import soccergame.model.players.GamePlayer;
import soccergame.model.players.Goalkeeper;
import soccergame.model.players.PlayerFactory;
import soccergame.model.players.Striker;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

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


}
