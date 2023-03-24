package br.com.dias.moviecardgame.abs.client;

import br.com.dias.moviecardgame.abs.domain.Movie;

import java.util.List;

/**
 * Interface to interact with external Movie API.
 */
public interface MovieApi {

    /**
     * Request the Movie API to load movies.
     *
     * @param search search term used to get movies
     * @param limit quantity limit to load
     * @return list of {@link Movie}
     */
    List<Movie> getMovies(String search, long limit);

}
