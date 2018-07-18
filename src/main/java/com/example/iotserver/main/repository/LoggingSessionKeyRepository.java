package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.LoggingSessionKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingSessionKeyRepository extends CrudRepository<LoggingSessionKey, Integer> {
}