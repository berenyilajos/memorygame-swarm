package hu.icell.managers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.icell.common.dto.ResultDTO;
import hu.icell.common.dto.UserDTO;
import hu.icell.dao.interfaces.ResultDaoLocal;
import hu.icell.dao.interfaces.ResultDataDaoLocal;
import hu.icell.dto.helper.DtoHelper;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;
import hu.icell.managers.interfaces.ResultManagerLocal;
import hu.icell.managers.interfaces.ResultManagerRemote;

@Stateless
public class ResultManagerEjb implements ResultManagerLocal, ResultManagerRemote {
	
    @EJB
    private ResultDaoLocal resultDao;

    @EJB
    private ResultDataDaoLocal resultDataDao;
    
    public List<ResultDTO> getResults() {
        return DtoHelper.resultsToDTO(resultDao.getResults());
    }
    
    public List<ResultDTO> getResultsData() {
      return DtoHelper.resultsToDTO(resultDao.getResultsData());
  }
    
    public List<ResultDTO> getResultsByUser(UserDTO user) {
        return DtoHelper.resultsToDTO(resultDao.getResultsByUser(DtoHelper.toEntity(user)));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        resultDao.saveResult(seconds, userId);
        resultDataDao.saveResultData(seconds, userId);
    }

    public List<ResultDTO> getResultsBetterOrEquals(long seconds) {
        return DtoHelper
                .resultsToDTO(resultDao.getResultsBetterOrEquals(seconds));
    }
    
}
