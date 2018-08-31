package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.Periphery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheryRepository extends CrudRepository<Periphery, Integer> {
    Iterable<Periphery> findAllByOwner(String owner);
}