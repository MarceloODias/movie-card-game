package br.com.dias.moviecardgame.webapp.controller;

import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.abs.dto.RoundResponse;
import br.com.dias.moviecardgame.abs.exception.GameException;
import br.com.dias.moviecardgame.abs.service.GameService;
import br.com.dias.moviecardgame.webapp.dto.ErrorResponse;
import br.com.dias.moviecardgame.webapp.dto.RoundRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

/**
 * Controller with the REST api methods of this project.
 * Note that this is only a http interface to access business layer.
 */
@RestController
@EnableWebMvc
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    /**
     * Default constructor to inject dependencies.
     *
     * @param gameService the {@link GameService} which apply the game rules
     */
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    /**
     * Lists and returns the Users ranked by accumulated points.
     *
     * @param offset offset value to paginate - 0 if not informed
     * @param limit limit value to paginate - 10 if not informed
     *
     * @return the list of users sorted descending by accumulated points
     */
    @RequestMapping(path = "/ranking",
            method = RequestMethod.GET,
            produces = {"application/json"})
    public List<User> ranking(@RequestParam(required = false, defaultValue = "0") int offset,
                              @RequestParam(required = false, defaultValue = "10") int limit) {
        return gameService.getRanking(offset, limit);
    }

    /**
     * Starts a new game. If the user has already played, this resets his points.
     * This method loads user data from the auth object.
     * But one option can be using a filter to encapsulates this logic.
     *
     * @param auth the user authenticated data, this is loaded using jwt token
     *
     * @return the game first round
     *
     * @throws GameException if user player has already a game not finished.
     */
    @RequestMapping(path = "/game/start",
            method = RequestMethod.POST,
            produces = {"application/json"})
    public RoundResponse startNewGame(JwtAuthenticationToken auth) throws GameException {
        return gameService.startGame(getUserId(auth), getUserName(auth));
    }

    /**
     * Ends the current game being played.
     * This method loads user data from the auth object.
     * But one option can be using a filter to encapsulates this logic.
     *
     * @param auth the user authenticated data, this is loaded using jwt token
     *
     * @return {@link RoundResponse} containing only the game summary
     *
     * @throws GameException if user player has not a game to finish.
     */
    @RequestMapping(path = "/game/end",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    public RoundResponse endGame(JwtAuthenticationToken auth) throws GameException {
        return gameService.endGame(getUserId(auth));
    }

    /**
     * Process a user answer to a round game.
     * A new round data will be returned, unless user reached the limit of committing 3 errors.
     * Ends the current game being played.
     * This method loads user data from the auth object.
     * But one option can be using a filter to encapsulates this logic.
     *
     * @param request {@link RoundRequestDto} containing the request data containing the movieId answered
     * @param auth the user authenticated data, this is loaded using jwt token
     *
     * @return {@link RoundResponse} containing the new round data, or containing only the game summary
     *
     * @throws GameException if user player has not an active round game to play
     */
    @RequestMapping(path = "/game/answer", method = RequestMethod.POST, produces = {"application/json"})
    public RoundResponse processAnswer(@RequestBody RoundRequestDto request, JwtAuthenticationToken auth) throws GameException {
        request.setUserId(getUserId(auth));
        return gameService.processAnswer(request);
    }

    /**
     * Gets the userId.
     *
     * @param auth user data to extract userId
     *
     * @return the userId
     */
    private String getUserId(JwtAuthenticationToken auth){
        String userId = auth.getToken().getClaimAsString("preferred_username");
        if (userId == null){
            userId = getName(auth);
        }
        return userId;
    }

    /**
     * Gets the username.
     *
     * @param auth user data to extract username
     * @return the username
     */
    private String getUserName(JwtAuthenticationToken auth){
        String username = auth.getToken().getClaimAsString("name");
        if (username == null){
            username = getName(auth);
        }
        return username;
    }

    /**
     * Gets the simple name for this token.
     *
     * @param auth the auth jwt token
     * @return the simple name
     */
    private String getName(JwtAuthenticationToken auth){
        return auth.getName();
    }

    /**
     * Default exception handler.
     *
     * @param ex error thrown
     * @return {@link ResponseEntity} encapsulating the error
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(Exception ex){

        LOG.error("Error:", ex);

        ErrorResponse resp = new ErrorResponse();
        resp.setErrorType(ex.getClass().getSimpleName());
        resp.setErrorMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }
}
