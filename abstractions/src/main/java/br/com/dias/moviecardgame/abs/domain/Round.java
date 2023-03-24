package br.com.dias.moviecardgame.abs.domain;

/**
 * Interface to abstract the Round class.
 */
public interface Round {

    /**
     * Format used to compose the identifier uniquely using the userId, and both movies ids.
     */
    String ROUND_ID_FORMAT = "%s-%s-%s";

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    String getId();

    /**
     * Sets the identifier.
     *
     * @param id the identifier
     */
    void setId(String id);

    /**
     * Checks if this Round was already used.
     * This is important to track the Movie Pair already sent to the User.
     *
     * @return {@code false} if not used, {@code true} otherwise
     */
    boolean isUsed();

    /**
     * Sets if this Round was already used.
     *
     * @param used to set as used or not
     */
    void setUsed(boolean used);

    /**
     * Checks if this Round was already answered.
     * This is important to track the Movie Pair already answered by the User.
     *
     * @return {@code false} if not answered, {@code true} otherwise
     */
    boolean isAnswered();

    /**
     * Sets if this Round was already answered.
     *
     * @@param answered {@code false} if not answered, {@code true} otherwise
     */
    void setAnswered(boolean answered);

    /**
     * Gets the id of the Movie with the max point in this Round.
     *
     * @return the movieId
     */
    String getMovieIdToWin();

    /**
     * Sets the id of the Movie with the max point in this Round.
     *
     * @param movieIdToWin the movieId
     */
    void setMovieIdToWin(String movieIdToWin);

}
