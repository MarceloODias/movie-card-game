package br.com.dias.moviecardgame.repo.dao;

import br.com.dias.moviecardgame.abs.dao.MovieDao;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.repo.access.MovieRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard implementation of DAO for internal Movie table.
 */
public class BaseMovieDao implements MovieDao {

    private final MovieRepositoryAccess movieRepositoryAccess;

    /**
     * Constructor to inject dependency.
     *
     * @param movieRepositoryAccess CRUD interface to manipulate Movies
     */
    public BaseMovieDao(MovieRepositoryAccess movieRepositoryAccess){
        this.movieRepositoryAccess = movieRepositoryAccess;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(List<Movie> movies) {
        this.movieRepositoryAccess.saveAll(convert(movies));
    }

    /**
     * Converts a list of  {@link Movie} to a list of {@link BaseMovie},
     * which is the standard mapped entity class for Movie table.
     *
     * @param movies a list of {@link Movie} to convert
     * @return a list of {@link BaseMovie} converted
     */
    private List<BaseMovie> convert(List<Movie> movies) {
        ArrayList<BaseMovie> baseMovies = new ArrayList<>();
        for (Movie movie: movies) {
            baseMovies.add(convert(movie));
        }
        return baseMovies;
    }

    /**
     * Converts (if needed) {@link Movie} to {@link BaseMovie},
     * which is the standard mapped entity class for Movie table.
     *
     * @param movie the {@link Movie} to convert
     * @return the {@link BaseMovie} converted
     */
    private BaseMovie convert(Movie movie){
        if (movie instanceof BaseMovie){
            return (BaseMovie) movie;
        }
        return BaseMovie.builder(movie).build();
    }
}
