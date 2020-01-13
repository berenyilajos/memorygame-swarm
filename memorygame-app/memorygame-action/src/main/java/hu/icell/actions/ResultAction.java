package hu.icell.actions;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import hu.icell.dao.ResultDataDao;
import hu.icell.dao.qualifier.Memorygame2Database;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;
//import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icell.dao.ResultDao;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Model
public class ResultAction {
    @Inject
    private ResultDao resultDao;

    @Inject
    private ResultDataDao resultDataDao;
    
    public List<Result> getResults() {
        return resultDao.getResults();
    }
    
    public List<Result> getResultsData() {
      return resultDao.getResultsData();
  }
    
    public List<Result> getResultsByUser(User user) {
        return resultDao.getResultsByUser(user);
    }
    
//    @Transactional(qualifier = Memorygame2Database.class)
    @Transactional
//    @EntityManagerConfig(qualifier = Memorygame2Database.class)
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        resultDao.saveResult(seconds, userId);
        resultDataDao.saveResultData(seconds, userId);
    }
    
}
