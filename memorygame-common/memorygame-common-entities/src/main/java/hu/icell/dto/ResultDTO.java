package hu.icell.dto;

import java.util.Date;

public class ResultDTO {
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
//		this.resultDate = new Date(resultDate.getTime());
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
