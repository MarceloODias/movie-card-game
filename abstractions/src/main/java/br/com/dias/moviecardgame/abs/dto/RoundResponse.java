package br.com.dias.moviecardgame.abs.dto;

import br.com.dias.moviecardgame.abs.domain.Movie;

import java.util.List;

/**
 * Interface to abstract the DTO Response class used to 'ask' User one new Round.
 */
public interface RoundResponse {

    /**
     * Sets the alternative movies.
     * Selecting the right one between them, the user earn one point.
     *
     * @param movies The alternative movies
     */
    void setMovies(List<Movie> movies);

    /**
     * Gets the alternative movies.
     * Selecting the right one between them, the user earn one point.
     *
     * @return the alternative movies
     */
    List<Movie> getMovies();

    /**
     * Sets the round count number of this Round.
     *
     * @param roundNumber the Round Number
     */
    void setRoundNumber(int roundNumber);

    /**
     * Gets the round count number of this Round.
     *
     * @return the round number
     */
    int getRoundNumber();

    /**
     * Sets the error count of this Round.
     * This represents the User's error quantity at the moment.
     *
     * @param errorCount the error count.
     */
    void setErrorCount(int errorCount);

    /**
     * Gets the error count of this Round.
     * This represents the User's error quantity at the moment.
     *
     * @return the error count.
     */
    int getErrorCount();

    /**
     * Sets the message to display to User.
     *
     * @param message the message
     */
    void setMessage(String message);

    /**
     * Gets the message to display to User.
     *
     * @return the message
     */
    String getMessage();

    /**
     * Sets the total points User accumulated.
     *
     * @param points the total points
     */
    void setPointsAccumulated(int points);

    /**
     * gets the total points User accumulated.
     *
     * @return the total points
     */
    int getPointsAccumulated();

}
