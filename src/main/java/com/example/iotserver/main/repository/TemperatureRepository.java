package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.Temperature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Integer> {
    Iterable<Temperature> findAllByOwner(String owner);
}