package hu.icell.managers.interfaces;

import java.util.List;

import javax.ejb.Local;

import hu.icell.entities.ResultData;
import hu.icell.exception.MyApplicationException;

@Local
public interface ResultDataManagerLocal {
	
	List<ResultData> getResults();
	
	List<ResultData> getResultsDatas();
	
	void saveResultData(int seconds, long userId) throws MyApplicationException;

}
