package hu.icell.dao.databean;

import java.math.BigDecimal;
import java.util.Date;

public class ResultDatabean {
    private BigDecimal userId;
    private String username;
    private String email;
    private String password;

    private BigDecimal resultId;
    private Date resultDate;
    private BigDecimal seconds;

    public ResultDatabean(BigDecimal userId, String username, String email, String password, BigDecimal resultId, java.sql.Timestamp resultDate, BigDecimal seconds) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.resultId = resultId;
        this.resultDate = new Date(resultDate.getTime());
        this.seconds = seconds;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
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

    public BigDecimal getResultId() {
        return resultId;
    }

    public void setResultId(BigDecimal resultId) {
        this.resultId = resultId;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public BigDecimal getSeconds() {
        return seconds;
    }

    public void setSeconds(BigDecimal seconds) {
        this.seconds = seconds;
    }

}
