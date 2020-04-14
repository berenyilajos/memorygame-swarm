package hu.icell.common.data.dto;

import java.io.Serializable;
import java.util.Date;

public class ResultDataDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private Date resultDate;
	private long seconds;
	private long userId;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
