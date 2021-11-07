package soccergame.model.players;

/**
 * A data-structure to store a {@code GamePlayer} object's game statistics.
 */
public class PlayerStatistics {
    /**
     * An abstract score of a {@code GamePlayer}
     */
    private Integer score = 0;

    /**
     * Get the statistics attribute of this {@code PlayerStatistics} object.
     *
     * @return the score of this {@code PlayerStatistics object}
     */
    public Integer getStatistics() {
        return score;
    }

    /**
     * Set the statistics attribute of this {@code PlayerStatistics} object
     *
     * @param score the new score to set for this {@code PlayerStatistics} object
     */
    public void setStatistics(Integer score) {
        this.score = score;
    }

    /**
     * Get the string representation of this {@code PlayerStatistics}'s score.
     *
     * @return the score of this {@code PlayerStatistics} as a string
     */
    @Override
    public String toString() {
        return score.toString();
    }
}
