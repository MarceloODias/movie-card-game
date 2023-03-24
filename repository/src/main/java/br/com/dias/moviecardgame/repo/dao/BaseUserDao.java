package br.com.dias.moviecardgame.repo.dao;

import br.com.dias.moviecardgame.abs.dao.UserDao;
import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.repo.access.UserRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Standard implementation of DAO for internal User table.
 */
public class BaseUserDao implements UserDao {
    private UserRepositoryAccess userRepositoryAccess;

    /**
     * Constructor to inject dependency.
     *
     * @param userRepositoryAccess CRUD interface to manipulate Users
     */
    public BaseUserDao(UserRepositoryAccess userRepositoryAccess){
        this.userRepositoryAccess = userRepositoryAccess;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List listRankedUser(int offset, int limit){

        int pagNumber = offset % limit;
        Pageable sortedByPointsDesc =
                PageRequest.of(pagNumber, limit, Sort.by("points").descending());

        return userRepositoryAccess.findAll(sortedByPointsDesc).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUser(User user) {
        this.userRepositoryAccess.save((BaseUser) user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String userId) {
        return userRepositoryAccess
                .findById(userId)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User addNewUser(String userId, String name){
        return this.userRepositoryAccess.save(BaseUser.builder()
                        .id(userId)
                        .name(name)
                        .rounds(0)
                        .roundsWon(0)
                        .points(0)
                        .finished(false)
                .build());
    }

}
