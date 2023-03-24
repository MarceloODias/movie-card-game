package br.com.dias.moviecardgame.repo.access.dao.test;

import br.com.dias.moviecardgame.abs.domain.User;
import br.com.dias.moviecardgame.repo.access.UserRepositoryAccess;
import br.com.dias.moviecardgame.repo.access.domain.BaseUser;
import br.com.dias.moviecardgame.repo.dao.BaseUserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests case for {@link BaseUserDao}.
 */
public class BaseUserDaoTest {

    private BaseUserDao classUnderTest;

    private UserRepositoryAccess userRepositoryAccess;

    private static final String USER_ID_ONE = "userIdOne";
    private static final String USER_ID_TWO = "userIdtwo";

    /**
     * Setup method
     */
    @BeforeEach
    public void setUp() {
        //mock
        userRepositoryAccess = mock(UserRepositoryAccess.class);

        //construct with mocks
        classUnderTest = new BaseUserDao(userRepositoryAccess);
    }

    /**
     * Checks that {@link BaseUserDao#saveUser(User)} returns without error and calls underling crud interface.
     */
    @Test
    public void testSaveWhenAllFineExpectNoError(){

        //given
        String userId = USER_ID_ONE;
        BaseUser user = BaseUser.builder().id(userId).build();

        //when
        classUnderTest.saveUser(user);

        //then
        verify(userRepositoryAccess).save(any());
    }

    /**
     * Checks that {@link BaseUserDao#addNewUser(String, String)} returns without error and calls underling crud interface.
     */
    @Test
    public void testAddNewUserWhenAllFineExpectNoError() {

        //given
        String userId = USER_ID_ONE;
        String name = "name";
        BaseUser expectedUser = BaseUser.builder().id(userId).name(name).build();
        when(userRepositoryAccess.save(any())).thenReturn(expectedUser);

        //when
        User actual = classUnderTest.addNewUser(userId, name);

        //then
        verify(userRepositoryAccess).save(any());
        assertEquals(actual.getId(), expectedUser.getId());
        assertEquals(actual.getName(), expectedUser.getName());
    }

    /**
     * Checks that {@link BaseUserDao#getUser(String)})} returns without error and calls underling crud interface.
     */
    @Test
    public void testGetUserWhenAllFineExpectNoError(){

        //given
        String userId = USER_ID_ONE;
        BaseUser expectedUser = BaseUser.builder().id(userId).build();
        when(userRepositoryAccess.findById(userId)).thenReturn(Optional.ofNullable(expectedUser));

        //when
        User actual = classUnderTest.getUser(userId);

        //then
        verify(userRepositoryAccess).findById(userId);
        assertNotNull(expectedUser);
        assertNotNull(actual);
        assertEquals(actual.getId(), expectedUser.getId());
    }

    /**
     * Checks that {@link BaseUserDao#listRankedUser(int, int)})}
     * returns without error and calls underling crud interface.
     */
    @Test
    public void testListRankedUserWhenAllFineExpectNoError(){

        //given
        int offset = 0;
        int limit = 2;
        BaseUser userOne = BaseUser.builder().id(USER_ID_ONE).build();
        BaseUser userTwo = BaseUser.builder().id(USER_ID_TWO).build();
        List<BaseUser> list = Arrays.asList(userOne, userTwo);
        Page pageMock = mock(Page.class);
        when(pageMock.toList()).thenReturn(list);
        Pageable pageable = PageRequest.of(offset, limit);

        when(userRepositoryAccess.findAll(any(Pageable.class))).thenReturn(pageMock);

        //when
        List actualList = classUnderTest.listRankedUser(offset, limit);

        //then
        assertEquals(actualList, list);
    }
}
