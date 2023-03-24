package br.com.dias.moviecardgame.abs.domain;

/**
 * Interface to abstract the User class.
 */
public interface User {

    /**
     * Gets the User Id.
     *
     * @return the id
     */
    String getId();

    /**
     * Sets the User id.
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Gets the User's name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the User's name
     *
     * @param name the name
     */
    void setName(String name);

    /**
     * Gets the User Rounds count.
     *
     * @return
     */
    int getRounds();

    /**
     * Sets the User rounds count
     *
     * @param rounds the Rounds
     */
    void setRounds(int rounds);

    /**
     * Gets the User rounds won count.
     *
     * @return the User rounds won count
     */
    int getRoundsWon();

    /**
     * Sets the User rounds won count.
     *
     * @param roundsWon the User won count
     */
    void setRoundsWon(int roundsWon);

    /**
     * Gets the total accumulated points.
     * The User can try again, in this case this number is reseted.
     *
     * @return the total points
     */
    int getPoints();

    /**
     * Sets the total accumulated points.
     * The User can try again, in this case this number is reseted.
     *
     * @param points the total points
     */
    void setPoints(int points);

    /**
     * Checks if the User finished the game.
     * The game finishes when user errors 3 times, or when call 'end' endpoint.
     *
     * @return {@code true} if finished, or {@code false} otherwise
     */
    boolean isFinished();

    /**
     * Sets if the User finished the game.
     * The game finishes when user errors 3 times, or when call 'end' endpoint.
     *
     * @param finished the finished flag
     */
    void setFinished(boolean finished);


}
