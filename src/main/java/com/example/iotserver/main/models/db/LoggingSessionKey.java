package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "logging_session_keys")
@EntityListeners(AuditingEntityListener.class)
public class LoggingSessionKey {
    @Id
    @Column(name = "session")
    private String sessionTemporaryKey;

    @Column(name = "mail")
    private String mail;

    LoggingSessionKey() {
    }

    public LoggingSessionKey(String sessionTemporaryKey, String mail) {
        this.sessionTemporaryKey = sessionTemporaryKey;
        this.mail = mail;
    }

    public String getSessionTemporaryKey() {
        return sessionTemporaryKey;
    }

    public String getMail() {
        return mail;
    }

    public void setSessionTemporaryKey(String sessionTemporaryKey) {
        this.sessionTemporaryKey = sessionTemporaryKey;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
