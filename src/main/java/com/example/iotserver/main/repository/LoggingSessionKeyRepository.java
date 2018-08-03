package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.LoggingSessionKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

@Repository
public interface LoggingSessionKeyRepository extends CrudRepository<LoggingSessionKey, Integer> {
    LoggingSessionKey findBySessionTemporaryKey(String session);
    LoggingSessionKey findByMail(String mail);
    Iterable<LoggingSessionKey> findAllByMail(String mail);
}