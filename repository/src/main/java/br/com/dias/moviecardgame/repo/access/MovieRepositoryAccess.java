package br.com.dias.moviecardgame.repo.access;

import br.com.dias.moviecardgame.repo.access.domain.BaseMovie;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD interface to manipulated Movies.
 */
public interface MovieRepositoryAccess  extends CrudRepository<BaseMovie, String> {
}
