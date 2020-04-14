package hu.icell.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<ResultDTO> results = new ArrayList<>();

	public ResultsDTO add(ResultDTO resultDTO) {
		results.add(resultDTO);
		return this;
	}

	public List<ResultDTO> getResults() {
		return results;
	}

	public void setResults(List<ResultDTO> results) {
		this.results = results;
	}

}
