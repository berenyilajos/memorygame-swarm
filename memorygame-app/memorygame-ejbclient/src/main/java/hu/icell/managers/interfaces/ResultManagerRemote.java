package hu.icell.managers.interfaces;

import java.util.List;

import javax.ejb.Remote;

import hu.icell.common.dto.ResultDTO;
import hu.icell.common.dto.UserDTO;
import hu.icell.exception.MyApplicationException;

@Remote
public interface ResultManagerRemote {
	
	List<ResultDTO> getResults();
	
	List<ResultDTO> getResultsData();
	
	List<ResultDTO> getResultsByUser(UserDTO user);
	
	void saveResult(int seconds, long userId) throws MyApplicationException;

}
