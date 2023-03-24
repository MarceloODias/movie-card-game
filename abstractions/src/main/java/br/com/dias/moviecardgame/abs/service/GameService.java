package br.com.dias.moviecardgame.abs.service;

import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.abs.dto.RoundRequest;
import br.com.dias.moviecardgame.abs.dto.RoundResponse;
import br.com.dias.moviecardgame.abs.exception.GameException;

import java.util.List;

/**
 * Interface to abstract the principal internal business service in this project.
 */
public interface GameService {

    /**
     * Lists and returns the Users ranked by accumulated points.
     *
     * @param offset offset value to paginate
     * @param limit limit value to paginate
     *
     * @return the list of users sorted descending by accumulated points
     */
    List<User> getRanking(int offset, int limit);

    /**
     * Starts a new game. If the user has already played, this resets his points.
     *
     * @param userId the userId playing
     * @param name the player name
     *
     * @return the game first round
     *
     * @throws GameException if user player has already a game not finished.
     */
    RoundResponse startGame(String userId, String name) throws GameException;

    /**
     * Ends the current game being played.
     *
     * @param userId the player userId
     * @return {@link RoundResponse} containing only the game summary
     * @throws GameException if user player has not a game to finish.
     */
    RoundResponse endGame(String userId) throws GameException;

    /**
     * Process a user answer to a round game.
     * A new round data will be returned, unless user reached the limit of committing 3 errors.
     *
     * @param request {@link RoundRequest} containing the request data containing the movieId answered
     * @return {@link RoundResponse} containing the new round data, or containing only the game summary
     *
     * @throws GameException if user player has not an active round game to play
     */
    RoundResponse processAnswer(RoundRequest request) throws GameException;

}
