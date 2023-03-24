package br.com.dias.moviecardgame.abs.dao;

import br.com.dias.moviecardgame.abs.domain.Movie;

import java.util.List;

/**
 * Interface to access internal Movie persistence.
 */
public interface MovieDao {

    /**
     * Saves a list of {@link Movie}
     *
     * @param movies the list of {@link Movie} to save
     */
    void saveAll(List<Movie> movies);

}
