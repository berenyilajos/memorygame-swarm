package hu.icell.actions;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.assertj.core.api.Assertions;

import hu.icell.dao.AuthDao;
import hu.icell.entities.User;
import hu.icell.exception.UserAllreadyExistException;

@RunWith(MockitoJUnitRunner.class)
public class IndexActionTest {
    
    @InjectMocks
    private IndexAction underTest;
    
    @Mock
    private AuthDao authDao;
    
    @Mock
    private User testUser;
    
    private final String usernameExist = "lajos";
    private final String usernameNotExist = "lujo";
    private final String password = "password";
    private final String wrongPassword = "wrongPassword";
    
    @Before
    public void setUp() throws Exception {
        doThrow(new UserAllreadyExistException()).when(authDao).saveUser(usernameExist, password);
        when(authDao.getUserByUsername(usernameExist)).thenReturn(testUser);
        when(authDao.getUserByUsername(usernameNotExist)).thenReturn(null);
        when(authDao.getUserByUsernameAndPassword(usernameExist, password)).thenReturn(testUser);
        when(authDao.getUserByUsernameAndPassword(usernameExist, wrongPassword)).thenReturn(null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserByUsername() {
        User user = underTest.getUserByUsername(usernameExist);
        assertEquals(testUser, user);
        user = underTest.getUserByUsername(usernameNotExist);
        assertEquals(null, user);
        verify(authDao, times(2)).getUserByUsername(Matchers.startsWith("l"));
    }

    @Test
    public void testGetUserByUsernameAndPassword() {
        User user = underTest.getUserByUsernameAndPassword(usernameExist, password);
        assertEquals(testUser, user);
        user = underTest.getUserByUsernameAndPassword(usernameNotExist, wrongPassword);
        assertEquals(null, user);
        verify(authDao, times(2)).getUserByUsernameAndPassword(Matchers.startsWith("l"), Matchers.anyString());
    }

    @Test
    public void testSaveUser() throws UserAllreadyExistException {
        underTest.saveUser(usernameNotExist, password);
        verify(authDao, times(1)).saveUser(usernameNotExist, password);
        Assertions.assertThatExceptionOfType(UserAllreadyExistException.class)
                .isThrownBy(() ->  underTest.saveUser(usernameExist, password))
                .withMessage(UserAllreadyExistException.USER_ALLREADY_EXISTS);
        assertThat(assertThrows(UserAllreadyExistException.class, () -> underTest.saveUser(usernameExist, password)).getMessage(),
                startsWith(UserAllreadyExistException.USER_ALLREADY_EXISTS));
        assertEquals(assertThrows(UserAllreadyExistException.class, () -> underTest.saveUser(usernameExist, password)).getMessage(),
                UserAllreadyExistException.USER_ALLREADY_EXISTS);

    }
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public void testSaveUserWithExceptionMessage() throws UserAllreadyExistException {
        underTest.saveUser(usernameNotExist, password);
        verify(authDao, times(1)).saveUser(usernameNotExist, password);
        expectedEx.expect(UserAllreadyExistException.class);
        expectedEx.expectMessage(UserAllreadyExistException.USER_ALLREADY_EXISTS);
        underTest.saveUser(usernameExist, password);
        fail("Should not be called!");
    }

}
