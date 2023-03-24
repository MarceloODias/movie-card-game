package br.com.dias.moviecardgame.repo.access.dao.test;


import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.repo.access.MovieRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseMovie;
import br.com.dias.moviecardgame.repo.dao.BaseMovieDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests case for {@link BaseMovieDao}.
 */
public class BaseMovieDaoTest {

    private BaseMovieDao classUnderTest;

    private MovieRepositoryAccess movieRepositoryAccess;

    @BeforeEach
    public void setUp() {
        //mock
        movieRepositoryAccess = mock(MovieRepositoryAccess.class);

        //construct with mocks
        classUnderTest = new BaseMovieDao(movieRepositoryAccess);
    }

    /**
     * Checks that {@link BaseMovieDao#saveAll(List)} returns without error and calls underling crud interface.
     */
    @Test
    public void testSaveAllWhenAllFineExpectNoError(){

        //given
        List<Movie> movies = Collections.singletonList(getSomeMovie());

        //when
        classUnderTest.saveAll(movies);

        //then
        verify(movieRepositoryAccess).saveAll(any());
    }

    private Movie getSomeMovie(){
        BaseMovie movie = new BaseMovie();
        movie.setId("id");
        return movie;
    }

}
