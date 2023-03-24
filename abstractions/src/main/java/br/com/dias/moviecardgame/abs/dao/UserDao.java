package br.com.dias.moviecardgame.abs.dao;

import br.com.dias.moviecardgame.abs.domain.User;

import java.util.List;

/**
 *  Interface to access internal User persistence.
 *  It's important to mention that this table has only project specific User data.
 *  Username, password and name are stored at the Authentication Provider.
 */
public interface UserDao {

    /**
     * Saves the {@link User) at internal table.
     *
     * @param user the instance to be persisted
     */
    void saveUser(User user);

    /**
     * Lists the Users that has points (i.e. Users that have started the game at least once).
     * This method returned the list sorted descending by points accumulated.
     *
     * @param offset offset value used to paginate
     * @param limit limit value used to paginate
     * @return the list of {@link User}
     */
    List<User> listRankedUser(int offset, int limit);

    /**
     * Loads a specific {@link User} by the userId
     *
     * @param userId userId value used to load the {@link User}
     * @return the {@link User} found if exists
     */
    User getUser(String userId);

    /**
     * Persists a new User data which started a game.
     *
     * @param userId the user identifier
     * @param name the user name
     *
     * @return the {@link User} persisted
     */
    User addNewUser(String userId, String name);
}
