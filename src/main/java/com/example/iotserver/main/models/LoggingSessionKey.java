package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "logging_session_keys")
@EntityListeners(AuditingEntityListener.class)
public class LoggingSessionKey {
    @Id
    private String logging_session_key;

    @Column
    private String user_mail;

    LoggingSessionKey(){
    }

    public LoggingSessionKey(String logging_session_key, String user_mail) {
        this.logging_session_key = logging_session_key;
        this.user_mail = user_mail;
    }

    public String getLogging_session_key() { return logging_session_key; }

    public String getUser_mail() { return user_mail; }

    public void setLogging_session_key(String logging_session_key) { this.logging_session_key = logging_session_key; }

    public void setUser_mail(String user_mail) { this.user_mail = user_mail; }
}
