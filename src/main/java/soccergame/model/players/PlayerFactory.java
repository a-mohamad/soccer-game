package soccergame.model.players;

import java.awt.*;

/**
 * A {@code GamePlayer} factory class for instantiating a player.
 */
public class PlayerFactory {

    /**
     * Construct a {@code GamePlayer} based on the specified player name type.
     *
     * @param playerName the player name to give the new {@code GamePlayer}
     * @return a new {@code GamePlayer} with class instance of the {@code playerNaem}
     */
    public final GamePlayer getPlayer(String playerName) {
        return switch (playerName.toLowerCase()) {
            case "striker" -> new Striker("striker", Color.BLUE);
            case "goalkeeper" -> new Goalkeeper("goalkeeper", Color.YELLOW);
            default -> throw new IllegalArgumentException("Not a player type.");
        };
    }
}
