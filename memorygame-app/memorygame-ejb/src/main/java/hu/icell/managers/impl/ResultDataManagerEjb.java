package hu.icell.managers.impl;

import hu.icell.common.data.dto.ResultDataDTO;
import hu.icell.dao.interfaces.ResultDataDaoLocal;
import hu.icell.dao.repositories.ResultDataRepository;
import hu.icell.dto.helper.DtoHelper;
import hu.icell.exception.MyApplicationException;
import hu.icell.managers.interfaces.ResultDataManagerLocal;
import hu.icell.managers.interfaces.ResultDataManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;


@Stateless
public class ResultDataManagerEjb implements ResultDataManagerLocal, ResultDataManagerRemote {
    @EJB
    private ResultDataDaoLocal resultDataDao;

    @Inject
    private ResultDataRepository resultDataRepository;
    
    public List<ResultDataDTO> getResults() {
        return DtoHelper.resultDatasToDTO(resultDataRepository.getResultDatas());
    }
    
    public List<ResultDataDTO> getResultsDatas() {
        return DtoHelper.resultDatasToDTO(resultDataDao.getResultDatas());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveResultData(int seconds, long userId) throws MyApplicationException {
        resultDataDao.saveResultData(seconds, userId);
    }
}
