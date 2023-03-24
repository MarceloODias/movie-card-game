package br.com.dias.moviecardgame.repo.access;

import br.com.dias.moviecardgame.repo.access.domain.BaseUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * CRUD interface to manipulated Users.
 */
public interface UserRepositoryAccess extends CrudRepository<BaseUser, String>, PagingAndSortingRepository<BaseUser, String> {
}
