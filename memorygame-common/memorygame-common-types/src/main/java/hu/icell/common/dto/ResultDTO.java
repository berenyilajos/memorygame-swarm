package hu.icell.common.dto;


import java.io.Serializable;
import java.util.Date;

public class ResultDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private Date resultDate;
	private long seconds;
	private String username;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public long getSeconds() {
		return seconds;
	}
	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
