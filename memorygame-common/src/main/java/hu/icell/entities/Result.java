package hu.icell.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the "RESULT" database table.
 * 
 */
@Entity
@Table(name="\"RESULT\"")
@NamedQuery(name="Result.findAll", query="SELECT r FROM Result r")
@SequenceGenerator(name = "RESULT_SEQ", sequenceName = "RESULT_SEQ", initialValue = 1, allocationSize = 0)
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SEQ")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="RESULT_DATE")
	private Date resultDate;

	@Column(name="\"SECONDS\"")
	private BigDecimal seconds;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	public Result() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getResultDate() {
		return this.resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public BigDecimal getSeconds() {
		return this.seconds;
	}

	public void setSeconds(BigDecimal seconds) {
		this.seconds = seconds;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public String toString() {
        return "Result [id=" + id + ", resultDate=" + resultDate + ", seconds=" + seconds + ", user=" + user + "]";
    }

}