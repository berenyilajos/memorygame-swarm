package hu.icell.actions;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

//import javax.transaction.Transactional;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icell.dao.ResultDao;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Model
public class ResultAction {
    @Inject
    private ResultDao resultDao;
    
    public List<Result> getResults() {
        return resultDao.getResults();
    }
    
    public List<Result> getResultsData() {
      return resultDao.getResultsData();
  }
    
    public List<Result> getResultsByUser(User user) {
        return resultDao.getResultsByUser(user);
    }
    
    @Transactional
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        resultDao.saveResult(seconds, userId);
    }
    
}
