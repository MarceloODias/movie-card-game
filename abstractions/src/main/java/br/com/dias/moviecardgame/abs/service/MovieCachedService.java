package br.com.dias.moviecardgame.abs.service;

import br.com.dias.moviecardgame.abs.domain.Movie;

/**
 * Interface to abstract the service responsible to cache movies.
 */
public interface MovieCachedService {

    /**
     * Starts loading the movie list.
     */
    void startLoad();

    /**
     * Checks if the movies were loaded.
     *
     * @return {@code false} if not loaded, or {@code true} otherwise
     */
    boolean isLoaded();

    /**
     * Gets a new random movie to compose a game round.
     *
     * @return a {@link Movie} randomly selected
     */
    Movie getRandomMovie();
}
