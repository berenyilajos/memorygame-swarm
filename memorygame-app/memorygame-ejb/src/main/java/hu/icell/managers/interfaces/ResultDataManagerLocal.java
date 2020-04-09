package hu.icell.managers.interfaces;

import java.util.List;

import javax.ejb.Local;

import hu.icell.common.data.dto.ResultDataDTO;
import hu.icell.exception.MyApplicationException;

@Local
public interface ResultDataManagerLocal {
	
	List<ResultDataDTO> getResults();
	
	List<ResultDataDTO> getResultsDatas();
	
	void saveResultData(int seconds, long userId) throws MyApplicationException;

}
