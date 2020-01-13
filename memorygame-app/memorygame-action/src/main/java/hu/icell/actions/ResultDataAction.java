package hu.icell.actions;

import hu.icell.dao.ResultDataDao;
import hu.icell.dao.repositories.ResultDataRepository;
import hu.icell.entities.ResultData;
import hu.icell.exception.MyApplicationException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;


@Model
public class ResultDataAction {
    @Inject
    private ResultDataDao resultDataDao;

    @Inject
    private ResultDataRepository resultDataRepository;
    
    public List<ResultData> getResults() {
        return resultDataRepository.getResultDatas();
    }
    
    public List<ResultData> getResultsDatas() {
        return resultDataDao.getResultDatas();
    }

    @Transactional
    public void saveResultData(int seconds, long userId) throws MyApplicationException {
        resultDataDao.saveResultData(seconds, userId);
    }
}
