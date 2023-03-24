package br.com.dias.moviecardgame.webapp.config;

import br.com.dias.moviecardgame.abs.client.MovieApi;
import br.com.dias.moviecardgame.abs.dao.MovieDao;
import br.com.dias.moviecardgame.abs.dao.RoundDao;
import br.com.dias.moviecardgame.abs.dao.UserDao;
import br.com.dias.moviecardgame.abs.service.GameService;
import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import br.com.dias.moviecardgame.bus.service.BaseGameService;
import br.com.dias.moviecardgame.loader.BaseMovieCachedService;
import br.com.dias.moviecardgame.loader.client.ImdbApiClient;
import br.com.dias.moviecardgame.repo.access.MovieRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.RoundRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.UserRepositoryAccess;
import br.com.dias.moviecardgame.repo.dao.BaseMovieDao;
import br.com.dias.moviecardgame.repo.dao.BaseRoundDao;
import br.com.dias.moviecardgame.repo.dao.BaseUserDao;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * Configuration class to perform the necessary settings and injections.
 */
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"br.com.dias.moviecardgame.repo.access"})
@EntityScan("br.com.dias.moviecardgame.repo.access.domain")
public class ApplicationConfiguration {

    @Value("${movie.api.endpoint}")
    private String movieApiEndpoint;

    @Value("${movie.api.key}")
    private String movieApiKey;

    @Value("${movie.search.keywords}")
    private List<String> movieKeywords;

    @Value("${movie.load.size}")
    private int movieLoadSize;

    /**
     * Creates a {@link GameService}.
     *
     * @param roundDao the dao to manipulate Round
     * @param userDao the dao to manipulate User
     * @param movieService the service to get movies
     *
     * @return an instance of {@link GameService}
     */
    @Bean
    public GameService gameService(RoundDao roundDao, UserDao userDao, MovieCachedService movieService){
        return new BaseGameService(roundDao, userDao, movieService);
    }

    /**
     * Creates a {@link MovieDao}.
     *
     * @param movieRepositoryAccess the CRUD interface to manipulate Movies
     *
     * @return an instance of {@link MovieDao}
     */
    @Bean
    public MovieDao movieDao(MovieRepositoryAccess movieRepositoryAccess){
        return new BaseMovieDao(movieRepositoryAccess);
    }

    /**
     * Creates a {@link RoundDao}.
     *
     * @param roundRepositoryAccess the CRUD interface to manipulate Rounds
     *
     * @return an instance of {@link RoundDao}
     */
    @Bean
    public RoundDao roundDao(RoundRepositoryAccess roundRepositoryAccess){
        return new BaseRoundDao(roundRepositoryAccess);
    }

    /**
     * Creates a {@link UserDao}.
     *
     * @param userRepositoryAccess the CRUD interface to manipulate User
     *
     * @return an instance of {@link UserDao}
     */
    @Bean
    public UserDao userDao(UserRepositoryAccess userRepositoryAccess){
        return new BaseUserDao(userRepositoryAccess);
    }

    /**
     * Creates a {@link Gson}.
     * If customizing the Gson settings is needed, please use this method.
     *
     * @return an instance of {@link Gson}
     */
    @Bean
    public Gson gson(){
        return new Gson();
    }

    /**
     * Creates a {@link MovieApi}.
     *
     * @param gson the {@link Gson} configured by this class
     *
     * @return an instance of {@link MovieApi}
     */
    @Bean
    public MovieApi movieApi(Gson gson){
        return new ImdbApiClient(gson, movieApiEndpoint, movieApiKey);
    }

    /**
     * Creates a {@link MovieCachedService}.
     *
     * @param movieApi the api to load movies
     * @param movieDao the dao to persist movies
     *
     * @return an instance of {@link MovieCachedService}
     */
    @Bean
    public MovieCachedService movieCachedService(MovieApi movieApi, MovieDao movieDao){
        MovieCachedService service = new BaseMovieCachedService(movieApi, movieDao, movieKeywords, movieLoadSize);
        service.startLoad();
        return service;
    }

}
