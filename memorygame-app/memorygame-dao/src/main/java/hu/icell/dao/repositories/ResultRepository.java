package hu.icell.dao.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.icell.dao.jpa.Memorygame2EntityManagerResolver;
import hu.icell.dao.qualifier.Memorygame2Database;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.entities.Result;
import hu.icell.entities.User;

@Repository(forEntity = Result.class)
@EntityManagerConfig(entityManagerResolver = Memorygame2EntityManagerResolver.class)
//public abstract class ResultRepository implements EntityRepository<Result, Long> {
public abstract class ResultRepository extends AbstractEntityRepository<Result, Long> {

    @Inject
    @ThisLogger
    private AppLogger log;
    
//    @Inject
//    @Memorygame2Database
//    private EntityManager em;
    
    @Query(value = "SELECT r FROM Result r JOIN FETCH r.user u WHERE u=:user ORDER BY r.seconds ASC, r.resultDate DESC", max = 15)
    abstract public List<Result> getResultsByUser(@QueryParam("user") User user);
    
    @Query(value = "SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC", max = 20)
    abstract public List<Result> getResults();

    public List<Result> getResultsEm() {
        log.debug("ResultRepository.getResultsEm >>>");
//        TypedQuery<Result> q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC", Result.class);
        TypedQuery<Result> q = typedQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC");
        q.setMaxResults(20);
        List<Result> list = q.getResultList();
        log.debug("<<< ResultRepository.getResultsEm");
  
        return list;
    }
    
    
    
}
