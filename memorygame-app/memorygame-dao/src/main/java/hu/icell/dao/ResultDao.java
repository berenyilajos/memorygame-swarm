package hu.icell.dao;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

//import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.dao.databean.ResultDatabean;
import hu.icell.dao.repositories.ResultRepository;
import hu.icell.dao.repositories.UserRepository;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Model
public class ResultDao {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    private EntityManager em;

    @Inject
    private ResultRepository resultRepository;

    @Inject
    private UserRepository userRepository;

    public static final int MIN_SECONDS = 10;
    public static final String LESS_SECONDS_MESSAGE = "Seconds értéke nem lehet " + MIN_SECONDS + "-nél kevesebb!";

    public List<Result> getResults() {
        log.debug("ResultDao.getResults >>>");
        // Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u
        // ORDER BY r.seconds ASC, r.resultDate DESC");
        // q.setMaxResults(20);
        // List<Result> list = q.getResultList();
        // List<Result> list = resultRepository.getResults();
        List<Result> list = resultRepository.getResultsEm();
        log.debug("<<< ResultDao.getResults");

        return list;
    }

    public List<Result> getResultsData() {
        log.debug("ResultDao.getResultsData >>>");
        Query q = em.createNativeQuery("SELECT u.id userId, u.username username, u.email email, u.password password, "
                + "r.id resultId, r.result_date resultDate, r.seconds seconds FROM RESULT r JOIN USERS u ON r.user_id=u.id ORDER BY seconds ASC, resultDate DESC");
        q.setMaxResults(20);
        List<Object[]> resultList =  q.getResultList();
        List<ResultDatabean> dataList = map(ResultDatabean.class, resultList);
        List<Result> list = new ArrayList<Result>();
        for (ResultDatabean bean : dataList) {
            User u = new User();
            u.setId(bean.getUserId());
            u.setEmail(bean.getEmail());
            u.setPassword(bean.getPassword());
            u.setUsername(bean.getUsername());
            Result r = new Result();
            r.setId(bean.getResultId());
            r.setResultDate(bean.getResultDate());
            r.setSeconds(bean.getSeconds());
            r.setUser(u);
            list.add(r);
        }
        log.debug("<<< ResultDao.getResultsData");

        return list;
    }

    public static <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleTypes = new ArrayList<Class<?>>();
        for (Object field : tuple) {
            tupleTypes.add(field.getClass());
        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> map(Class<T> type, List<Object[]> records) {
        List<T> result = new ArrayList<T>();
        for (Object[] record : records) {
            result.add(map(type, record));
        }
        return result;
    }

    public List<Result> getResultsByUser(User user) {
        log.debug("ResultDao.getResultsByUser, userName=[{}] >>>", user.getUsername());
        // Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u
        // WHERE u=:user ORDER BY r.seconds ASC, r.resultDate DESC");
        // q.setParameter("user", user);
        // q.setMaxResults(20);
        // List<Result> list = q.getResultList();
        List<Result> list = resultRepository.getResultsByUser(user);
        log.debug("<<< ResultDao.getResultsByUser");

        return list;
    }

    // @Transactional
    public void saveResult(int seconds, long userId) throws MyApplicationException {
        log.debug("ResultDao.saveResult, seconds=[{}], userId=[{}] >>>", seconds, userId);
        if (seconds < MIN_SECONDS) {
            log.warn(LESS_SECONDS_MESSAGE);
            throw new MyApplicationException(LESS_SECONDS_MESSAGE);
        }
        try {
            Result r = new Result();
            r.setResultDate(Calendar.getInstance().getTime());
            r.setSeconds(seconds);
            r.setUser(userRepository.findBy(userId));
            // em.persist(r);
            resultRepository.save(r);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new MyApplicationException(e.getMessage());
        }
        log.debug("<<< ResultDao.saveResult");
    }

}
