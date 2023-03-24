package br.com.dias.moviecardgame.repo.dao;

import br.com.dias.moviecardgame.abs.dao.RoundDao;
import br.com.dias.moviecardgame.abs.domain.Round;
import br.com.dias.moviecardgame.repo.access.RoundRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseRound;

/**
 * Standard implementation of DAO for internal Round table.
 */
public class BaseRoundDao implements RoundDao {

    private final RoundRepositoryAccess roundRepositoryAccess;

    /**
     * Constructor to inject dependency.
     *
     * @param roundRepositoryAccess CRUD interface to manipulate Rounds
     */
    public BaseRoundDao(RoundRepositoryAccess roundRepositoryAccess){
        this.roundRepositoryAccess = roundRepositoryAccess;
    }

    /** {@inheritDoc} */
    @Override
    public void save(Round round) {
        this.roundRepositoryAccess.save((BaseRound) round);
    }

    /** {@inheritDoc} */
    @Override
    public Round getRound(String roundId, String userId, String movieIdToWin) {
        return this.roundRepositoryAccess.findById(roundId).orElse(BaseRound.builder()
                .id(roundId)
                .userId(userId)
                .movieIdToWin(movieIdToWin)
                .used(false)
                .build());
    }

    /** {@inheritDoc} */
    @Override
    public Round getPendingRound(String userId) {
        return this.roundRepositoryAccess.findByUserIdAndAnswered(userId, false);
    }
}
