package soccergame.model.players;

import java.awt.*;

public class PlayerFactory {

    public GamePlayer getPlayer(String striker) {
        return switch (striker.toLowerCase()) {
            case "striker" -> new Striker("striker", Color.BLUE);
            case "goalkeeper" -> new Goalkeeper("goalkeeper", Color.YELLOW);
            default -> throw new IllegalArgumentException("Not a player type.");
        };
    }
}
