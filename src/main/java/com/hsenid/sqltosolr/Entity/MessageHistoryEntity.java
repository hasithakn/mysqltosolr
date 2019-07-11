package com.hsenid.sqltosolr.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "message_history", schema = "kite", catalog = "")
public class MessageHistoryEntity {
    private String appId;
    private String message;
    private String correlationId;
    private Timestamp receiveDate;

    @Basic
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "correlation_id")
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Basic
    @Column(name = "receive_date")
    public Timestamp getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Timestamp receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageHistoryEntity that = (MessageHistoryEntity) o;

        if (appId != null ? !appId.equals(that.appId) : that.appId != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (correlationId != null ? !correlationId.equals(that.correlationId) : that.correlationId != null)
            return false;
        if (receiveDate != null ? !receiveDate.equals(that.receiveDate) : that.receiveDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appId != null ? appId.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (correlationId != null ? correlationId.hashCode() : 0);
        result = 31 * result + (receiveDate != null ? receiveDate.hashCode() : 0);
        return result;
    }
}
