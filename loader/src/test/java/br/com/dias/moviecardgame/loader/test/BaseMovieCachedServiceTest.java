package br.com.dias.moviecardgame.loader.test;

import br.com.dias.moviecardgame.abs.client.MovieApi;
import br.com.dias.moviecardgame.abs.dao.MovieDao;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.loader.BaseMovieCachedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests case for {@link BaseMovieCachedService}.
 */
public class BaseMovieCachedServiceTest {

    private BaseMovieTestable classUnderTest;
    private MovieApi movieApi;

    @BeforeEach
    public void setup(){
        movieApi = mock(MovieApi.class);
        MovieDao movieDao = mock(MovieDao.class);
        classUnderTest = new BaseMovieTestable(movieApi, movieDao, Collections.singletonList("Matrix"), 1);
    }

    @Test
    public void testLoadWhenMoviesIsLoadedExpectLoaded() {
        // given
        List<Movie> movies = Collections.singletonList(mock(Movie.class));
        when(movieApi.getMovies(anyString(), anyLong())).thenReturn(movies);

        // when
        classUnderTest.doLoad();

        // then
        assertTrue(classUnderTest.isLoaded());

    }
}
