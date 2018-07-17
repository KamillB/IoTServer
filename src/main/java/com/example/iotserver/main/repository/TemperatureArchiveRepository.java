package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.TemperatureArchive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureArchiveRepository extends CrudRepository<TemperatureArchive, Integer> {
}