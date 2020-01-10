package hu.icell.actions;

import hu.icell.dao.ResultDataDao;
import hu.icell.dao.jpa.Memorygame3EntityManagerResolver;
import hu.icell.dao.qualifier.Memorygame3Database;
import hu.icell.dao.repositories.ResultDataRepository;
import hu.icell.entities.ResultData;
import hu.icell.exception.MyApplicationException;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
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
    
//    @Transactional(qualifier = Memorygame3Database.class)
    @Transactional
    @EntityManagerConfig(qualifier = Memorygame3Database.class)
    public void saveResultData(int seconds, long userId) throws MyApplicationException {
        resultDataDao.saveResultData(seconds, userId);
    }
    
}
