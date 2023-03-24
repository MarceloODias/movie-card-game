package br.com.dias.moviecardgame.repo.access.dao.test;

import br.com.dias.moviecardgame.abs.domain.Round;
import br.com.dias.moviecardgame.repo.access.RoundRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseRound;
import br.com.dias.moviecardgame.repo.dao.BaseRoundDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests case for {@link BaseRoundDao}.
 */
public class BaseRoundDaoTest {

    private BaseRoundDao classUnderTest;

    private RoundRepositoryAccess roundRepositoryAccess;

    @BeforeEach
    public void setUp() {
        //mock
        roundRepositoryAccess = mock(RoundRepositoryAccess.class);

        //construct with mocks
        classUnderTest = new BaseRoundDao(roundRepositoryAccess);
    }

    /**
     * Checks that {@link BaseRoundDao#save(Round)} returns without error and calls underling crud interface.
     */
    @Test
    public void testSaveWhenAllFineExpectNoError(){

        //given
        Round round = BaseRound.builder().id("round").build();

        //when
        classUnderTest.save(round);

        //then
        verify(roundRepositoryAccess).save(any());
    }

    /**
     * Checks that {@link BaseRoundDao#getRound(String, String, String)} returns the round
     * founded by the underline crud interface.
     */
    @Test
    public void testGetRoundWhenAllFineExpectRoundReturned(){

        //given
        String roundId = "roundId";
        String userId = "userId";
        String movieIdToWin = "movieId";

        BaseRound round = BaseRound.builder().id(roundId).build();
        when(roundRepositoryAccess.findById(roundId)).thenReturn(Optional.ofNullable(round));

        //when
        Round returnedRound = classUnderTest.getRound(roundId, userId, movieIdToWin);

        //then
        verify(roundRepositoryAccess).findById(roundId);
        assertEquals(round, returnedRound);
    }

    /**
     * Checks that {@link BaseRoundDao#getPendingRound(String)} returns the round
     * founded by the underline crud interface.
     */
    @Test
    public void testGetPendingRoundWhenAllFineExpectRoundReturned(){

        //given
        String userId = "userId";

        BaseRound round = BaseRound.builder().userId(userId).build();
        when(roundRepositoryAccess.findByUserIdAndAnswered(userId, false))
                .thenReturn(round);

        //when
        Round returnedRound = classUnderTest.getPendingRound(userId);

        //then
        verify(roundRepositoryAccess).findByUserIdAndAnswered(userId, false);
        assertEquals(round, returnedRound);
    }

}