package hu.icell.dao.impl;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

//import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.dao.databean.ParameterClassResolver;
import hu.icell.dao.databean.ResultDatabean;
import hu.icell.dao.interfaces.ResultDaoLocal;
import hu.icell.dao.qualifier.Memorygame2Database;
import hu.icell.dao.repositories.ResultRepository;
import hu.icell.dao.repositories.UserRepository;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Stateless
public class ResultDaoEjb implements ResultDaoLocal {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    @Memorygame2Database
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
        q.setMaxResults(30);
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

    public static <T extends ParameterClassResolver> T map(Class<T> type, Object[] tuple) {
        try {
            Constructor<T> ctor = type.getConstructor(type.getConstructor().newInstance().getClassByParameterIndex());
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends ParameterClassResolver> List<T> map(Class<T> type, List<Object[]> records) {
        List<T> result = new ArrayList<>(records.size());
        for (Object[] record : records) {
            result.add(map(type, record));
        }
        return result;
    }

    public List<Result> getResultsByUser(User user) {
        log.debug("ResultDao.getResultsByUser, userName=[{}] >>>", user.getUsername());
//        Query q = em.createQuery("SELECT u FROM User u JOIN FETCH u.results r WHERE u.id=:userId AND r.seconds < 90 ORDER BY r.seconds ASC, r.resultDate DESC");
//        q.setParameter("userId", user.getId());
//        // q.setMaxResults(20);
//        List<User> rlist = q.getResultList();
//        for (User u : rlist) {
//            log.info("User: " + u.debugString());
//        }
        List<Result> list = resultRepository.getResultsByUser(user);
        log.debug("<<< ResultDao.getResultsByUser");

        return list;
    }

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
            r.setUser(em.find(User.class, userId));
//            r.setUser(userRepository.findBy(userId));
             em.persist(r);
//            resultRepository.save(r);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new MyApplicationException(e.getMessage());
        }
        log.debug("<<< ResultDao.saveResult");
    }

    @Override
    public List<Result> getResultsBetterOrEquals(long seconds) {
        log.debug("ResultDao.getResultsBetterOrEquals, seconds=[{}] >>>", seconds);
        String queryText = "SELECT r FROM Result r WHERE r.seconds <= :seconds";
        TypedQuery<Result> query = em.createQuery(queryText, Result.class);
        query.setParameter("seconds", seconds);
        log.debug("<<< ResultDao.getResultsBetterOrEquals");
        return query.getResultList();
    }

}
