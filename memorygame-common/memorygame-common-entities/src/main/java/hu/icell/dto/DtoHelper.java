package hu.icell.dto;

import hu.icell.entities.Result;

public class DtoHelper {
	
	public static ResultDTO toDTO(Result result) {
		if (result == null) {
			return null;
		}
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setId(result.getId());
		resultDTO.setResultDate(result.getResultDate());
		resultDTO.setSeconds(result.getSeconds());
		resultDTO.setUsername(result.getUser().getUsername());
		return resultDTO;
	}

}
