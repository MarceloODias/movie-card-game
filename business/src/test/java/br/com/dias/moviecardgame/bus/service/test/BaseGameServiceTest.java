package br.com.dias.moviecardgame.bus.service.test;

import br.com.dias.moviecardgame.abs.dao.RoundDao;
import br.com.dias.moviecardgame.abs.dao.UserDao;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.abs.domain.Round;
import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.abs.dto.RoundRequest;
import br.com.dias.moviecardgame.abs.dto.RoundResponse;
import br.com.dias.moviecardgame.abs.exception.GameException;
import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import br.com.dias.moviecardgame.bus.service.BaseGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Tests case for {@link BaseGameService}.
 */
public class BaseGameServiceTest {

    private BaseGameService classUnderTest;

    //mocks
    private RoundDao roundDao;
    private UserDao userDao;
    private MovieCachedService movieService;

    private static final String USER_ID = "UserId";
    private static final String NAME = "Name";

    /**
     * Setup method
     */
    @BeforeEach
    public void setUp() {
        //mock
        roundDao = mock(RoundDao.class);
        userDao = mock(UserDao.class);
        movieService = mock(MovieCachedService.class);
        when(movieService.isLoaded()).thenReturn(true);

        //construct with mocks
        classUnderTest = new BaseGameService(roundDao, userDao, movieService);
    }

    /**
     * Tests that {@link BaseGameService#startGame(String, String)}
     * starts the game correctly.
     *
     * @throws GameException if errors occurs
     */
    @Test
    public void testStartGameWhenGameNotStartedExpectStartedWithoutError() throws GameException {
        // given
        String userId = USER_ID;
        String name = NAME;
        Round round = mock(Round.class);
        when(round.isUsed()).thenReturn(false);
        Movie movie = mock(Movie.class);
        User user = mock(User.class);
        when(user.isFinished()).thenReturn(true);
        when(roundDao.getRound(any(), any(), any())).thenReturn(round);
        when(movieService.getRandomMovie()).thenReturn(movie);
        when(userDao.getUser(userId)).thenReturn(user);

        // when
        RoundResponse resp = classUnderTest.startGame(userId, name);

        // then
        assertNotNull(resp);
        assertNotNull(resp.getMovies());
    }

    /**
     * Tests that {@link BaseGameService#startGame(String, String)}
     * returns error when cache is not loaded.
     *
     * @throws GameException if errors occurs
     */
    @Test
    public void testStartGameWhenCacheNotLoadedExpectError() throws GameException {
        // given
        String userId = USER_ID;
        String name = NAME;
        User user = mock(User.class);
        when(user.isFinished()).thenReturn(true);
        when(movieService.isLoaded()).thenReturn(false);
        when(userDao.getUser(userId)).thenReturn(user);

        // when
        // then
        assertThrows(GameException.class, () -> {classUnderTest.startGame(userId, name); });

    }

    /**
     * Tests that {@link BaseGameService#endGame(String)} ends the game correctly.
     *
     * @throws GameException if errors occurs
     */
    @Test
    public void testEndGameWhenGameStartedExpectStartedWithoutError() throws GameException {
        // given
        String userId = USER_ID;
        Round round = mock(Round.class);
        when(round.isUsed()).thenReturn(false);
        User user = mock(User.class);
        when(user.isFinished()).thenReturn(false);
        when(roundDao.getPendingRound(userId)).thenReturn(round);
        when(userDao.getUser(userId)).thenReturn(user);

        // when
        RoundResponse resp = classUnderTest.endGame(userId);

        // then
        assertNotNull(resp);
        assertNull(resp.getMovies());
    }

    /**
     * Tests that {@link BaseGameService#processAnswer(RoundRequest)}
     * process answer correctly.
     *
     * @throws GameException if errors occurs
     */
    @Test
    public void testProcessAnswerWhenGameStartedExpectNextRoundWithoutError() throws GameException {
        // given
        String userId = USER_ID;
        Round round = mock(Round.class);
        when(round.isUsed()).thenReturn(false);
        User user = mock(User.class);
        Movie movie = mock(Movie.class);
        when(user.isFinished()).thenReturn(false);
        when(roundDao.getPendingRound(userId)).thenReturn(round);
        when(userDao.getUser(userId)).thenReturn(user);
        RoundRequest roundRequest = mock(RoundRequest.class);
        when(roundRequest.getUserId()).thenReturn(userId);
        when(roundRequest.getAnswerMovieId()).thenReturn("");
        when(roundDao.getRound(any(), any(), any())).thenReturn(round);
        when(movieService.getRandomMovie()).thenReturn(movie);


        // when
        RoundResponse resp = classUnderTest.processAnswer(roundRequest);

        // then
        assertNotNull(resp);
        assertNotNull(resp.getMovies());
    }

    /**
     * Tests that {@link BaseGameService#getRanking(int, int)} returns the players ranked.
     */
    @Test
    public void testGetRankingWhenGamesStartedExpectReturningWithoutError() throws GameException {
        // given
        User userOne = mock(User.class);
        User userTwo = mock(User.class);
        List<User> expected = Arrays.asList(userOne, userTwo);
        when(userDao.listRankedUser(anyInt(), anyInt())).thenReturn(expected);

        // when
        List<User> actual = classUnderTest.getRanking(0, 10);

        // then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
