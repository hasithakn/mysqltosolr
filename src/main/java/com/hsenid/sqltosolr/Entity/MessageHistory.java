package com.hsenid.sqltosolr.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MessageHistory implements Serializable {
    @Column(
            name = "app_id"
    )
    private String app_id;
    @Column(
            name = "message"
    )
    private String message;
    @Column(
            name = "correlation_id"
    )
    private String correlation_id;
    @Column(
            name = "receive_date"
    )
    private Timestamp receive_date;

    public MessageHistory() {
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrelation_id() {
        return this.correlation_id;
    }

    public void setCorrelation_id(String correlation_id) {
        this.correlation_id = correlation_id;
    }

    public Timestamp getReceive_date() {
        return this.receive_date;
    }

    public void setReceive_date(Timestamp receive_date) {
        this.receive_date = receive_date;
    }
}
