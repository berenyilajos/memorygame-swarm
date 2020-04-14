package hu.icell.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String email;
	private String password;
	private List<ResultDTO> results = new ArrayList<>();

	public UserDTO add(ResultDTO resultDTO) {
		results.add(resultDTO);
		return this;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ResultDTO> getResults() {
		return results;
	}

	public void setResults(List<ResultDTO> results) {
		this.results = results;
	}
	
	

}
