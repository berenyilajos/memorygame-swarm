package hu.icell.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
//import javax.transaction.Transactional;

//import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Model
public class ResultDao {
    
    @Inject
    private EntityManager em;
    
    public static final int MIN_SECONDS = 10;
    public static final String LESS_SECONDS_MESSAGE = "Seconds értéke nem lehet " + MIN_SECONDS + "-nél kevesebb!";
    
    public List<Result> getResults() {
        Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC");
        q.setMaxResults(20);
        List<Result> list = q.getResultList();
        
        return list;
    }
    
    public List<Result> getResultsByUser(User user) {
        Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u WHERE u=:user ORDER BY r.seconds ASC, r.resultDate DESC");
        q.setParameter("user", user);
        q.setMaxResults(20);
        List<Result> list = q.getResultList();
        
        return list;
    }
    
//    @Transactional
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        if (seconds < MIN_SECONDS) {
            throw new MyApplicationException(LESS_SECONDS_MESSAGE);
        }
        Result r = new Result();
        r.setResultDate(Calendar.getInstance().getTime());
        r.setSeconds(BigDecimal.valueOf(seconds));
        r.setUser(em.find(User.class, userId));
        em.persist(r);
    }

}
