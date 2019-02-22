package hu.icell.dao.databean;

import java.math.BigDecimal;
import java.util.Date;

public class ResultDatabean implements ParameterClassResolver {
    private long userId;
    private String username;
    private String email;
    private String password;

    private long resultId;
    private Date resultDate;
    private long seconds;

    public ResultDatabean() {
    }

    public ResultDatabean(BigDecimal userId, String username, String email, String password, BigDecimal resultId, java.sql.Timestamp resultDate, BigDecimal seconds) {
        super();
        this.userId = userId != null ? userId.longValue() : 0;
        this.username = username;
        this.email = email;
        this.password = password;
        this.resultId = resultId != null ? resultId.longValue() : 0;
        this.resultDate = resultDate != null ? new Date(resultDate.getTime()) : null;
        this.seconds = seconds != null ? seconds.longValue() : 0;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
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

    @Override
    public Class[] getClassByParameterIndex() {
        return new Class[]{BigDecimal.class, String.class, String.class, String.class, BigDecimal.class, java.sql.Timestamp.class, BigDecimal.class};
    }
}
