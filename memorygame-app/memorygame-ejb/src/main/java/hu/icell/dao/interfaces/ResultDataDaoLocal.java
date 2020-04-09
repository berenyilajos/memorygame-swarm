package hu.icell.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import hu.icell.entities.ResultData;
import hu.icell.exception.MyApplicationException;

@Local
public interface ResultDataDaoLocal {
	
	List<ResultData> getResultDatas();
	
	void saveResultData(int seconds, long userId) throws MyApplicationException;

}
