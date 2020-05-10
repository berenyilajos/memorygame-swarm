package hu.icell.managers.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.icell.common.dto.ResultDTO;
import hu.icell.common.dto.UserDTO;
import hu.icell.dao.impl.ResultDaoEjb;
import hu.icell.dao.interfaces.ResultDaoLocal;
import hu.icell.dao.interfaces.ResultDataDaoLocal;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@RunWith(MockitoJUnitRunner.class)
public class ResultManagerEjbTest {
    
    @InjectMocks
    private ResultManagerEjb underTest;
    
    @Mock
    private ResultDaoLocal resultDao;

    @Mock
    private ResultDataDaoLocal resultDataDao;
    
    @Mock
    private Result testResult;
    
    @Mock
    private User testUser;
    
    @Mock
    private UserDTO testUserDTO;
    
    private List<Result> testList = new ArrayList<Result>();
    private List<Result> testListByUser = new ArrayList<Result>();
    
    private final int seconds = 100;
    private final int lessSeconds = 5;
    private final long userId = 10;
    
    @Before
    public void setUp() throws Exception {
        testListByUser.add(testResult);
        for (int i = 0; i < 5; i++) {
        	Result result = new Result();
        	result.setId(1L);
        	result.setId(50);
        	result.setResultDate(new Date());
        	User user = new User();
        	user.setUsername("Username");
        	result.setUser(user);
            testList.add(result);
        }
        when(resultDao.getResultsByUser(testUser)).thenReturn(testListByUser);
        when(resultDao.getResults()).thenReturn(testList);
        doThrow(new MyApplicationException(ResultDaoEjb.LESS_SECONDS_MESSAGE)).when(resultDao).saveResult(lessSeconds, userId);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetResults() {
        List<ResultDTO> list = underTest.getResults();
        verify(resultDao, times(1)).getResults();
    }

    @Test
    public void testGetResultsByUser() {
        List<ResultDTO> list = underTest.getResultsByUser(testUserDTO);
        verify(resultDao, times(1)).getResultsByUser(any());
    }

    @Test
    public void testSaveResult() throws MyApplicationException {
        underTest.saveResult(seconds, userId);
        underTest.saveResult(seconds, userId);
        underTest.saveResult(seconds, userId);
        verify(resultDao, times(3)).saveResult(seconds, userId);
        verify(resultDataDao, times(3)).saveResultData(seconds, userId);
    }
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public void testSaveResultFail() throws MyApplicationException {
        expectedEx.expect(MyApplicationException.class);
        expectedEx.expectMessage(ResultDaoEjb.LESS_SECONDS_MESSAGE);
        underTest.saveResult(lessSeconds, userId);
        fail("Should not be called!");
    }

}
