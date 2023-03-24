package br.com.dias.moviecardgame.loader;

import br.com.dias.moviecardgame.abs.client.MovieApi;
import br.com.dias.moviecardgame.abs.dao.MovieDao;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Standard implementation of {@link MovieCachedService}.
 */
public class BaseMovieCachedService implements MovieCachedService {

    private static final Logger LOG = LoggerFactory.getLogger(BaseMovieCachedService.class);

    private final List<String> keywords;
    private final int movieListSize;
    private final MovieApi movieApi;
    private final MovieDao movieDao;
    private final List<Movie> moviesCache;

    private boolean loaded;

    /**
     * Constructor to inject dependencies.
     *
     * @param movieApi api used to access movies
     * @param movieDao dao used to persist movies
     * @param keywords list of words used to search for movies through the api
     * @param movieListSize quantity of movies desired to be cached
     */
    public BaseMovieCachedService(MovieApi movieApi, MovieDao movieDao, List<String> keywords, int movieListSize){
        this.movieApi = movieApi;
        this.movieDao = movieDao;
        this.keywords = keywords;
        this.movieListSize = movieListSize;
        this.moviesCache = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public Movie getRandomMovie(){
        int index = (int)Math.floor(Math.random() * (moviesCache.size()-1));
        return moviesCache.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLoaded(){
        return loaded;
    }

    /** {@inheritDoc} */
    @Override
    public void startLoad(){
        new Thread(() -> load()).start();

    }

    /**
     * Loads the movies using the API.
     */
    protected void load(){
        LOG.info("Loader started.");
        Queue<String> keys = new LinkedList<>();
        keys.addAll(keywords);

        long count = 0;
        while( !keys.isEmpty() && count<movieListSize ){
            String search = keys.poll();
            long quantity = movieListSize - count;

            List<Movie> movies = movieApi.getMovies(search, quantity);
            movieDao.saveAll(movies);
            moviesCache.addAll(movies);

            count += movies.size();
        }
        loaded = true;
        LOG.info("Movies loaded!");
    }
}
