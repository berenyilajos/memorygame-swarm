package hu.icell.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;

@Local
public interface ResultDaoLocal {
	
	List<Result> getResults();
	
	List<Result> getResultsData();
	
	List<Result> getResultsByUser(User user);
	
	void saveResult(int seconds, long userId) throws MyApplicationException;

}
