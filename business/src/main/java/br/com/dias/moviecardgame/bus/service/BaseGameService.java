package br.com.dias.moviecardgame.bus.service;

import br.com.dias.moviecardgame.abs.dao.RoundDao;
import br.com.dias.moviecardgame.abs.dao.UserDao;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.abs.domain.Round;
import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.abs.dto.RoundRequest;
import br.com.dias.moviecardgame.abs.dto.RoundResponse;
import br.com.dias.moviecardgame.abs.exception.GameException;
import br.com.dias.moviecardgame.abs.service.GameService;
import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import br.com.dias.moviecardgame.bus.dto.BaseRoundResponse;
import br.com.dias.moviecardgame.bus.exception.BusinessGameException;

import java.util.List;

/**
 * Standard implementation of {@link GameService}.
 */
public class BaseGameService implements GameService {

    private final RoundDao roundDao;
    private final UserDao userDao;
    private final MovieCachedService movieService;
    private static final String NEXT_ROUND_MESSAGE = "Which movie has more points?";

    /**
     * Constructor to inject dependencies.
     *
     * @param roundDao {@link RoundDao} instance to manipulate persistence of Rounds
     * @param userDao {@link UserDao} instance to manipulate persistence of Users
     * @param movieService {@link MovieCachedService} instance to get movies to compose rounds
     */
    public BaseGameService(RoundDao roundDao, UserDao userDao, MovieCachedService movieService){
        this.userDao = userDao;
        this.roundDao = roundDao;
        this.movieService = movieService;
    }

    /** {@inheritDoc} */
    @Override
    public List<User> getRanking(int offset, int limit) {
        return userDao.listRankedUser(offset, limit);
    }

    /** {@inheritDoc} */
    @Override
    public RoundResponse startGame(String userId, String name) throws GameException {

        User user = userDao.getUser(userId);
        if (user == null){
            user = userDao.addNewUser(userId, name);
        } else if (!user.isFinished()) {
            throw new BusinessGameException("The user has game already started. Should finish it first.");
        }
        initializeUser(user);
        userDao.saveUser(user);

        return BaseRoundResponse.builder()
                .roundNumber(user.getRounds())
                .errorCount(user.getRounds() - user.getRoundsWon())
                .movies(getRoundMovies(userId))
                .message(NEXT_ROUND_MESSAGE)
                .build();
    }

    /**
     * Gets the movies to compose a new round.
     * After selecting the movies, a new Round is persisted as current pending round waiting for a user answer.
     *
     * @param userId the user id playing the game
     * @return the list of movies of this round
     *
     * @throws GameException if cache is not loaded yet.
     */
    private List<Movie> getRoundMovies(String userId) throws GameException {
        if (!movieService.isLoaded()) {
            throw new BusinessGameException("Movie cache not loaded yet. Please wait.");
        }

        Round round;
        Movie movieOne;
        Movie movieTwo;
        do {
            movieOne = movieService.getRandomMovie();
            movieTwo = movieService.getRandomMovie();

            String movieIdToWin = movieOne.getId();
            String roundIdentifier = String.format(Round.ROUND_ID_FORMAT, userId, movieOne.getId(), movieTwo.getId());

            if (movieOne.getRating() * movieOne.getVotes() < movieTwo.getRating() * movieTwo.getVotes()) {
                roundIdentifier = String.format(Round.ROUND_ID_FORMAT, userId, movieTwo.getId(), movieOne.getId());
                movieIdToWin = movieTwo.getId();
            }
            round = roundDao.getRound(roundIdentifier, userId, movieIdToWin);
        } while (round.isUsed());

        round.setUsed(true);
        roundDao.save(round);

        return List.of(movieOne, movieTwo);
    }

    /**
     * Initializes or resets the user properties.
     *
     * @param user the user being initialized
     */
    private void initializeUser(User user){
        user.setPoints(0);
        user.setRounds(0);
        user.setRoundsWon(0);
        user.setFinished(false);
    }

    /** {@inheritDoc} */
    @Override
    public RoundResponse endGame(String userId) throws GameException {
        User user = userDao.getUser(userId);
        if (user == null || user.isFinished()){
            throw new BusinessGameException("The user has not game to finish.");
        }

        Round round = roundDao.getPendingRound(userId);
        if (round != null){
            round.setAnswered(true);
            roundDao.save(round);
        }

        user.setFinished(true);
        userDao.saveUser(user);

        return BaseRoundResponse.builder()
                .roundNumber(user.getRounds())
                .errorCount(user.getRounds() - user.getRoundsWon())
                .message("Game ended")
                .pointsAccumulated(user.getPoints())
                .build();
    }

    /** {@inheritDoc} */
    @Override
    public RoundResponse processAnswer(RoundRequest request) throws GameException {

        User user = userDao.getUser(request.getUserId());
        Round round = roundDao.getPendingRound(request.getUserId());

        if (user.isFinished()) {
            throw new BusinessGameException("The user has not started game. Please start game.");
        }
        if (round == null) {
            throw new BusinessGameException("The user has not an active round waiting for answer.");
        }

        user.setRounds(user.getRounds()+1);
        if (request.getAnswerMovieId().equals(round.getMovieIdToWin())){
            user.setRoundsWon(user.getRoundsWon()+1);
            user.setPoints(user.getPoints()+1);
        }

        int errorCount = user.getRounds() - user.getRoundsWon();
        String message = NEXT_ROUND_MESSAGE;
        List<Movie> movies = null;

        if (errorCount >= 3) {
            user.setFinished(true);
            message = "The user errored 3 times.";
        } else {
            movies = getRoundMovies(user.getId());
        }
        userDao.saveUser(user);

        round.setAnswered(true);
        roundDao.save(round);

        return BaseRoundResponse.builder()
                .roundNumber(user.getRounds())
                .errorCount(user.getRounds() - user.getRoundsWon())
                .movies(movies)
                .message(message)
                .pointsAccumulated(user.getPoints())
                .build();
    }
}
