package soccergame.model;

import soccergame.model.players.GamePlayer;
import soccergame.model.players.Goalkeeper;
import soccergame.model.players.Striker;

import java.awt.*;

public class PlayerFactory {

    public GamePlayer getPlayer(String striker) {
        return switch (striker) {
            case "striker" -> new Striker("striker", Color.BLUE);
            case "goalkeeper" -> new Goalkeeper("goalkeeper", Color.YELLOW);
            default -> throw new IllegalArgumentException();
        };
    }
}
