package hu.icell.managers.interfaces;

import java.util.List;

import javax.ejb.Remote;

import hu.icell.common.data.dto.ResultDataDTO;
import hu.icell.exception.MyApplicationException;

@Remote
public interface ResultDataManagerRemote {
	
	List<ResultDataDTO> getResults();
	
	List<ResultDataDTO> getResultsDatas();
	
	void saveResultData(int seconds, long userId) throws MyApplicationException;

}
