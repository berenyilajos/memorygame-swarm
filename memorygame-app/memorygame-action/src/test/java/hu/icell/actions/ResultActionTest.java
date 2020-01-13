package hu.icell.actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import hu.icell.dao.ResultDataDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.icell.dao.ResultDao;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@RunWith(MockitoJUnitRunner.class)
public class ResultActionTest {
    
    @InjectMocks
    private ResultAction underTest;
    
    @Mock
    private ResultDao resultDao;

    @Mock
    private ResultDataDao resultDataDao;
    
    @Mock
    private Result testResult;
    
    @Mock
    private User testUser;
    
    private List<Result> testList = new ArrayList<Result>();
    private List<Result> testListByUser = new ArrayList<Result>();
    
    private final int seconds = 100;
    private final int lessSeconds = 5;
    private final long userId = 10;
    
    @Before
    public void setUp() throws Exception {
        testListByUser.add(testResult);
        for (int i = 0; i < 5; i++) {
            testList.add(new Result());
        }
        when(resultDao.getResultsByUser(testUser)).thenReturn(testListByUser);
        when(resultDao.getResults()).thenReturn(testList);
        doThrow(new MyApplicationException(ResultDao.LESS_SECONDS_MESSAGE)).when(resultDao).saveResult(lessSeconds, userId);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetResults() {
        List<Result> list = underTest.getResults();
        assertEquals(testList, list);
    }

    @Test
    public void testGetResultsByUser() {
        List<Result> list = underTest.getResultsByUser(testUser);
        assertEquals(testListByUser, list);
        verify(resultDao, times(1)).getResultsByUser(testUser);
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
        expectedEx.expectMessage(ResultDao.LESS_SECONDS_MESSAGE);
        underTest.saveResult(lessSeconds, userId);
        fail("Should not be called!");
    }

}
