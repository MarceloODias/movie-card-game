package br.com.dias.moviecardgame.repo.access;

import br.com.dias.moviecardgame.repo.access.domain.BaseRound;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD interface to manipulated Rounds.
 */
public interface RoundRepositoryAccess extends CrudRepository<BaseRound, String> {

    BaseRound findByUserIdAndAnswered(String userId, boolean answered);
    BaseRound findByUserId(String userId);

}
