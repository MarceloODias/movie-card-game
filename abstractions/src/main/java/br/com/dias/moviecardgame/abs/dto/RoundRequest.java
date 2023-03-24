package br.com.dias.moviecardgame.abs.dto;

/**
 * Interface to abstract the DTO Request class used to answer one Round.
 */
public interface RoundRequest {

    /**
     * Sets the usedId of this request.
     *
     * @param userId the id
     */
    void setUserId(String userId);

    /**
     * Gets the usedId of this request.
     *
     * @param userId the id
     */
    String getUserId();

    /**
     * Sets the movieId selected by the User to answer.
     *
     * @param answerMovieId the selected movieId
     */
    void setAnswerMovieId(String answerMovieId);

    /**
     * Gets the movieId selected by the User to answer.
     *
     * @param answerMovieId the selected movieId
     */
    String getAnswerMovieId();

}
