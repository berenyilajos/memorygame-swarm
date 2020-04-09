package hu.icell.dao.repositories;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.dao.jpa.Memorygame3EntityManagerResolver;
import hu.icell.entities.ResultData;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository(forEntity = ResultData.class)
@EntityManagerConfig(entityManagerResolver = Memorygame3EntityManagerResolver.class)
public abstract class ResultDataRepository extends AbstractEntityRepository<ResultData, Long> {

    @Inject
    @ThisLogger
    private AppLogger log;
    
    @Query(value = "SELECT r FROM ResultData r ORDER BY r.seconds ASC, r.resultDate DESC", max = 20)
    abstract public List<ResultData> getResultDatas();
    
}
