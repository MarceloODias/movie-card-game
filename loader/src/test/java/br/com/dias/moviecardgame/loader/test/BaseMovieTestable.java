package br.com.dias.moviecardgame.loader.test;

import br.com.dias.moviecardgame.abs.client.MovieApi;
import br.com.dias.moviecardgame.abs.dao.MovieDao;
import br.com.dias.moviecardgame.loader.BaseMovieCachedService;

import java.util.List;

public class BaseMovieTestable extends BaseMovieCachedService {

    /**
     * Constructor to inject dependencies.
     *
     * @param movieApi      api used to access movies
     * @param movieDao      dao used to persist movies
     * @param keywords      list of words used to search for movies through the api
     * @param movieListSize quantity of movies desired to be cached
     */
    public BaseMovieTestable(MovieApi movieApi, MovieDao movieDao, List<String> keywords, int movieListSize) {
        super(movieApi, movieDao, keywords, movieListSize);
    }

    /**
     * Exposing {@link BaseMovieCachedService#load()}.
     */
    public void doLoad(){
        super.load();
    }
}
