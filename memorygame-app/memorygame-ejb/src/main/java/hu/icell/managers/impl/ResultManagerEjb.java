package hu.icell.managers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.icell.dao.interfaces.ResultDaoLocal;
import hu.icell.dao.interfaces.ResultDataDaoLocal;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;
import hu.icell.managers.interfaces.ResultManagerLocal;

@Stateless
public class ResultManagerEjb implements ResultManagerLocal {
	
    @EJB
    private ResultDaoLocal resultDao;

    @EJB
    private ResultDataDaoLocal resultDataDao;
    
    public List<Result> getResults() {
        return resultDao.getResults();
    }
    
    public List<Result> getResultsData() {
      return resultDao.getResultsData();
  }
    
    public List<Result> getResultsByUser(User user) {
        return resultDao.getResultsByUser(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        resultDao.saveResult(seconds, userId);
        resultDataDao.saveResultData(seconds, userId);
    }
    
}
