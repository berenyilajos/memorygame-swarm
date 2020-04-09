package hu.icell.dao.impl;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.dao.interfaces.ResultDataDaoLocal;
import hu.icell.dao.qualifier.Memorygame3Database;
import hu.icell.entities.ResultData;
import hu.icell.exception.MyApplicationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;


@Stateless
public class ResultDataDaoEjb implements ResultDataDaoLocal {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    @Memorygame3Database
    private EntityManager em;

    public static final int MIN_SECONDS = 10;
    public static final String LESS_SECONDS_MESSAGE = "Seconds értéke nem lehet " + MIN_SECONDS + "-nél kevesebb!";

    public List<ResultData> getResultDatas() {
        log.debug("ResultDataDao.getResultDatas >>>");
        TypedQuery q = em.createQuery("SELECT r FROM ResultData r ORDER BY r.seconds ASC, r.resultDate DESC", ResultData.class);
        q.setMaxResults(20);
        List<ResultData> list = q.getResultList();
        log.debug("<<< ResultDataDao.getResultDatas");

        return list;
    }

    public void saveResultData(int seconds, long userId) throws MyApplicationException {
        log.debug("ResultDataDao.saveResultData, seconds=[{}], userId=[{}] >>>", seconds, userId);
        if (seconds < MIN_SECONDS) {
            log.warn(LESS_SECONDS_MESSAGE);
            throw new MyApplicationException(LESS_SECONDS_MESSAGE);
        }
        try {
            ResultData r = new ResultData();
            r.setResultDate(Calendar.getInstance().getTime());
            r.setSeconds(seconds);
            r.setUserId(userId);
            em.merge(r);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new MyApplicationException(e.getMessage());
        }
        log.debug("<<< ResultDataDao.saveResultData");
    }

}
