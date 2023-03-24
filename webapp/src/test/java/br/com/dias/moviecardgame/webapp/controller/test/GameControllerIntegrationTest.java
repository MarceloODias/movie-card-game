package br.com.dias.moviecardgame.webapp.controller.test;

import br.com.dias.moviecardgame.abs.exception.GameException;
import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import br.com.dias.moviecardgame.webapp.dto.RoundRequestDto;
import br.com.dias.moviecardgame.webapp.test.ApplicationForTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.TimeUnit;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests case for integration.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationForTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MovieCachedService cachedService;

    private MockMvc mockMvc;

    private static final String ANSWER = "{\"answerMovieId\": \"123456\"}";
    private static final String ROLE = "PLAYER";

    /**
     * Setups the REST and Security configurations.
     *
     * @throws InterruptedException if sleep faces errors
     */
    @BeforeEach
    public void setup() throws InterruptedException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //necessary due to warming the cache up.
        while (!cachedService.isLoaded()){
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#startNewGame(JwtAuthenticationToken)}
     * returns forbidden (403) for not authenticated users.
     *
     * @throws Exception if errors occur.
     */
    @Order(1)
    @Test
    public void testStartWhenPlayerNotAuthenticatedExpectReturningUnauthorized() throws Exception {
        mockMvc.perform(post("/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#endGame(JwtAuthenticationToken)}
     * returns forbidden (403) for not authenticated users.
     *
     * @throws Exception if errors occur.
     */
    @Order(2)
    @Test
    public void testEndWhenPlayerNotAuthenticatedExpectReturningUnauthorized() throws Exception {
        mockMvc.perform(post("/game/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#processAnswer(RoundRequestDto, JwtAuthenticationToken)}
     * returns forbidden (403) for not authenticated users.
     *
     * @throws Exception if errors occur.
     */
    @Order(3)
    @Test
    public void testAnswerWhenPlayerNotAuthenticatedExpectReturningUnauthorized() throws Exception {
        mockMvc.perform(post("/game/answer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#processAnswer(RoundRequestDto, JwtAuthenticationToken)}
     * answer automatically ends the game after three errors.
     *
     * @throws Exception if errors occur.
     */
    @Order(4)
    @Test
    public void testAnswerWhenPlayerErrorThreeTimesExpectReturningError() throws Exception {
        mockMvc.perform(post("/game/start")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //sending 3 errors
        for (int i=0 ; i<3 ;i++) {
            mockMvc.perform(post("/game/answer").content(ANSWER)
                            .with(SecurityMockMvcRequestPostProcessors.jwt()
                                    .authorities(new SimpleGrantedAuthority(ROLE)))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        //trying to continue
        mockMvc.perform(post("/game/answer").content(ANSWER)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        //trying to end
        mockMvc.perform(post("/game/end")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#startNewGame(JwtAuthenticationToken)}
     * starts the game correctly when valid user is sent.
     *
     * @throws Exception if errors occur.
     */
    @Order(5)
    @Test
    public void testStartWhenPlayerNotStartExpectReturningNextRound() throws Exception {
        mockMvc.perform(post("/game/start")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Checks that {@link br.com.dias.moviecardgame.webapp.controller.GameController#processAnswer(RoundRequestDto, JwtAuthenticationToken)}
     * answer the game correctly when valid user is sent. The answer will be wrong because of it's fixed.
     *
     * @throws Exception if errors occur.
     */
    @Order(6)
    @Test
    public void testAnswerWhenPlayerNotStartExpectReturningNextRound() throws Exception {
        mockMvc.perform(post("/game/answer").content(ANSWER)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Checks {@link br.com.dias.moviecardgame.webapp.controller.GameController#endGame(JwtAuthenticationToken)}
     * ends the game correctly when valid user is sent.
     *
     * @throws Exception if errors occur.
     */
    @Order(7)
    @Test
    public void testEndWhenPlayerStartedExpectReturningOk() throws Exception {
        mockMvc.perform(post("/game/end")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Checks {@link br.com.dias.moviecardgame.webapp.controller.GameController#ranking(int, int)}
     * return users that played.
     *
     * @throws Exception if errors occur.
     */
    @Order(8)
    @Test
    public void testRankingWhenUserPlayedExpectReturningUsers() throws Exception {
        mockMvc.perform(get("/ranking")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Checks {@link br.com.dias.moviecardgame.webapp.controller.GameController#endGame(JwtAuthenticationToken)}
     * returns error when called by a user that does not have a game started.
     *
     * @throws Exception if errors occur.
     */
    @Order(9)
    @Test
    public void testEndWhenPlayerNotStartedExpectReturningError() throws Exception {
        mockMvc.perform(post("/game/end")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Checks {@link br.com.dias.moviecardgame.webapp.controller.GameController#processAnswer(RoundRequestDto, JwtAuthenticationToken)}
     * returns error when called by a user that does not have a game started.
     *
     * @throws Exception if errors occur.
     */
    @Order(10)
    @Test
    public void testAnswerWhenPlayerNotStartedExpectReturningError() throws Exception {
        mockMvc.perform(post("/game/answer").content(ANSWER)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Checks {@link br.com.dias.moviecardgame.webapp.controller.GameController#startNewGame(JwtAuthenticationToken)}
     * returns error when called by a user that does already have a game started.
     *
     * @throws Exception if errors occur.
     */
    @Order(11)
    @Test
    public void testStartWhenPlayerHasStartedExpectReturningError() throws Exception {
        mockMvc.perform(post("/game/start")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/game/start")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority(ROLE)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}