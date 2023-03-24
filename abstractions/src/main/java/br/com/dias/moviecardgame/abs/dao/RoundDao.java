package br.com.dias.moviecardgame.abs.dao;

import br.com.dias.moviecardgame.abs.domain.Round;

/**
 * Interface to access internal Round persistence.
 */
public interface RoundDao {

    /**
     * Saves an instance of {@link Round}
     * @param round the instance to save
     */
    void save(Round round);

    /**
     * Gets a {@link Round} based on id. If it does not exists a new instance is returned.
     *
     * @param roundId the id to search for the instance
     * @param userId the userId of this {@link Round}
     * @param movieIdToWin the movieId with most points of this {@link Round}
     * @return the instance of {@link Round}
     */
    Round getRound(String roundId, String userId, String movieIdToWin);

    /**
     * Gets (if exists) the current and pending instance of {@link Round} of a User.
     *
     * @param userId the user id
     * @return the instance of {@link Round} that is pending the User's answer
     */
    Round getPendingRound(String userId);
}
